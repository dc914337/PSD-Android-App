using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using PSD.Locales;

namespace PSD
{
    public partial class PSDForm : Form
    {
        private DataConnections _connections;
        private PasswordsList _passwords;

        public PSDForm(DataConnections dataConnections)
        {
            InitializeComponent();
            _connections = dataConnections;
            _passwords = _connections.PcBase.Base.Passwords;
            lstViewPasswords.HideSelection = false;
        }


        private void PSDForm_Load(object sender, EventArgs e)
        {
            if (!CheckBase())
            {
                MessageBox.Show(Localization.InputDataError);
                this.Close();
                return;
            }
            FillPathesLables();
            RefillPasswordsList();
        }

        private bool CheckBase()
        {
            if (_connections?.PcBase?.Base?.Passwords == null)
                return false;
            if (string.IsNullOrWhiteSpace(_connections.PcBase.Path))
                return false;
            return true;
        }

        private void FillPathesLables()
        {
            if (!string.IsNullOrWhiteSpace(_connections.PcBase.Path))
            {
                lblBasePath.Text = _connections.PcBase.Path;
                lblBasePathDesc.Visible = true;
            }
            if (!string.IsNullOrWhiteSpace(_connections.PhoneBase.Path))
            {
                lblAndroidBasePath.Text = _connections.PhoneBase.Path;
                lblAndroidPathDesc.Visible = true;
            }
            if (_connections.PsdBase.Connected)
            {
                lblPsdComPort.Text = _connections.PsdBase.Name.ToString();
                lblPsdConnectionDesc.Visible = true;
            }
        }

        private void RefillPasswordsList()
        {
            lstViewPasswords.Items.Clear();
            var passwords = _connections.PcBase.Base.Passwords.OrderBy(a => a.Id).ToList();

            for (int i = 0; i < passwords.Count; i++)
            {
                var currPass = passwords[i];
                var item = new ListViewItem(
                    new string[] {
                        currPass.Title,
                        currPass.Login });
                lstViewPasswords.Items.Add(item);
            }
        }





        private bool CheckPassword(PassItem pass)
        {

            if (Contains(pass))
            {
                MessageBox.Show("Contains this id");
                return false;
            }

            if (string.IsNullOrWhiteSpace(pass.Title))
            {
                MessageBox.Show("Title is empty");
                return false;
            }

            if (string.IsNullOrWhiteSpace(pass.Login))
            {
                MessageBox.Show("Login is empty");
                return false;
            }

            if (string.IsNullOrWhiteSpace(pass.Pass))
            {
                MessageBox.Show("Password is empty");
                return false;
            }

            if (string.IsNullOrWhiteSpace(pass.Description))
            {
                MessageBox.Show("Description is empty");
                return false;
            }
            return true;
        }

        private bool Contains(PassItem pass)
        {
            return _connections.PcBase.Base.Passwords.Any(a => a.Id == pass.Id && a != pass);
        }

        private PassItem GetFirstSelectedPassword()
        {
            return GetSelectedPasswords().FirstOrDefault();
        }


        private PassItem[] GetSelectedPasswords()
        {
            if (!_passwords.Any())
                return new PassItem[0];
            if (lstViewPasswords.SelectedIndices.Count <= 0)
                return new PassItem[0];
            PassItem[] selectedItems = new PassItem[lstViewPasswords.SelectedItems.Count];
            for (uint i = 0; i < selectedItems.Length; i++)
            {
                var passId = int.Parse(lstViewPasswords.SelectedItems[(int)i].SubItems[0].Text);
                selectedItems[i] = _passwords.FirstOrDefault(a => a.Id == passId);
            }
            return selectedItems;
        }

        private int GetLastIndex()
        {
            if (!_passwords.Any())
                return -1;
            return _passwords.Last().Id;
        }

        private void btnAddPass_Click(object sender, EventArgs e)
        {
            PassItem newPassword = new PassItem
            {
                Id = (ushort)(GetLastIndex() + 1)//can fail
            };


            if (!EditPassword(newPassword)) return;
            _passwords.Add(newPassword);
            UpdateData();
        }


        private void ReindexPasswords()
        {
            ushort newIndex = 0;
            foreach (var pass in _passwords.OrderBy(a => a.Id))
            {
                pass.Id = newIndex++;
            }
        }


        private bool EditPassword(PassItem password)
        {
            do
            {
                var editPassForm = new EditPasswordForm(password);
                editPassForm.ShowDialog();
                if (!editPassForm.Confirmed)
                    return false; //was cancelled
            } while (!CheckPassword(password));
            return true;
        }


        private void btnSaveAll_Click(object sender, EventArgs e)
        {
            ReindexPasswords();
            RefillPasswordsList();
            _connections.UpdateInAllAvailableBases();
        }

        private void lstViewPasswords_DoubleClick(object sender, EventArgs e)
        {
            var selectedPass = GetFirstSelectedPassword();
            var backup = selectedPass.GetCopy();
            if (!EditPassword(selectedPass))
                selectedPass.InitFromPass(backup);
            UpdateData();
        }

        private void btnRemovePass_Click(object sender, EventArgs e)
        {
            foreach (var selectedPass in GetSelectedPasswords())
            {
                _passwords.Remove(selectedPass);
            }
            UpdateData();
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            UpdateData();
        }

        private void UpdateData()
        {
            ReindexPasswords();
            RefillPasswordsList();
        }

        private void btnMoveUp_Click(object sender, EventArgs e)
        {
            var selectedPass = GetFirstSelectedPassword();
            if (selectedPass == null)
                return;

            var prevPass = _passwords.LastOrDefault(a => a.Id == selectedPass.Id - 1);//we suppose that array has no spaces
            if (prevPass == null)
                return;
            var tempId = selectedPass.Id;
            selectedPass.Id = prevPass.Id;
            prevPass.Id = tempId;
            UpdateData();
        }

        private void btnMoveDown_Click(object sender, EventArgs e)
        {
            var selectedPass = GetFirstSelectedPassword();
            if (selectedPass == null)
                return;
            var nextPass = _passwords.FirstOrDefault(a => a.Id == selectedPass.Id + 1);//we suppose that array has no spaces
            if (nextPass == null)
                return;
            var tempId = selectedPass.Id;
            selectedPass.Id = nextPass.Id;
            nextPass.Id = tempId;
            UpdateData();
        }
    }
}
