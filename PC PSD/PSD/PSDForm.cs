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

        private DateTime _lastChanges;


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
            BindLables();
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


        private void BindLables()
        {
            lblBasePath.DataBindings.Clear();
            lblBasePath.DataBindings.Add(new Binding("Text", _connections.PcBase, "Path"));
            lblBasePath.DataBindings.Add(new Binding("Visible", _connections.PcBase, "Connected"));
            lblBasePathDesc.DataBindings.Clear();
            lblBasePathDesc.DataBindings.Add(new Binding("Visible", _connections.PcBase, "Connected"));

            lblAndroidBasePath.DataBindings.Clear();
            lblAndroidBasePath.DataBindings.Add(new Binding("Text", _connections.PhoneBase, "Path"));
            lblAndroidBasePath.DataBindings.Add(new Binding("Visible", _connections.PhoneBase, "Connected"));
            lblAndroidPathDesc.DataBindings.Clear();
            lblAndroidPathDesc.DataBindings.Add(new Binding("Visible", _connections.PhoneBase, "Connected"));

            lblPsdConnected.DataBindings.Clear();
            lblPsdConnected.DataBindings.Add(new Binding("Text", _connections.PsdBase, "Name"));
            lblPsdConnected.DataBindings.Add(new Binding("Visible", _connections.PsdBase, "Connected"));
            lblPsdConnectionDesc.DataBindings.Clear();
            lblPsdConnectionDesc.DataBindings.Add(new Binding("Visible", _connections.PsdBase, "Connected"));
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
                var passId = lstViewPasswords.SelectedItems[(int)i].Index;
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


            if (!EditPassword(newPassword))
                return;
            RegisterChange();

            _passwords.Add(newPassword);
            UpdateData();
        }


        private void RegisterChange()
        {
            _lastChanges = DateTime.Now;
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
            SaveAll();
        }




        private void SaveAll()
        {
            ReindexPasswords();
            RefillPasswordsList();

            if (!_connections.UpdateInAllAvailableBases())
            {
                MessageBox.Show(Localization.UpdatingAllError);
            }
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
            RegisterChange();
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
            SwapItems(selectedPass, prevPass);
            RegisterChange();
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
            SwapItems(selectedPass, nextPass);
            RegisterChange();
            UpdateData();
        }


        private static void SwapItems(PassItem pass1, PassItem pass2)
        {
            var tempId = pass1.Id;
            pass1.Id = pass2.Id;
            pass2.Id = tempId;
        }


        private bool ExitCheck()
        {
            //check if not saved
            if (_connections.AllUpToDate(_lastChanges))
                return true;

            return MessageBox.Show(
                Localization.ExitQuestion,
                Localization.ExitQuestionFormText,
                MessageBoxButtons.YesNo)
                   == DialogResult.Yes;
        }


        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (ExitCheck())
                Application.Exit();
        }

        private void PSDForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!ExitCheck())
                e.Cancel = true;
        }

        private void saveAllToolStripMenuItem_Click(object sender, EventArgs e)
        {
            SaveAll();
        }

        private void savePcMenuItem_Click(object sender, EventArgs e)
        {
            if (!_connections.PcBase.Connected)
                MessageBox.Show(Localization.NotConnectedWarning);

            if (!_connections.UpdateIfConnected(_connections.PcBase))
            {
                MessageBox.Show(Localization.PsdUpdatingError);
            }
        }

        private void savePhoneMenuItem_Click(object sender, EventArgs e)
        {
            if (!_connections.PhoneBase.Connected)
                MessageBox.Show(Localization.NotConnectedWarning);

            if (!_connections.UpdateIfConnected(_connections.PhoneBase))
            {
                MessageBox.Show(Localization.PhoneUpdateError);
            }
        }

        private void saveToPsdMenuItem_Click(object sender, EventArgs e)
        {
            if (!_connections.PsdBase.Connected)
                MessageBox.Show(Localization.NotConnectedWarning);

            if (!_connections.UpdateIfConnected(_connections.PsdBase))
            {
                MessageBox.Show(Localization.PsdUpdateError);
            }
        }

        private void newPCBaseMenuItem_Click(object sender, EventArgs e)
        {

        }


        /* private void CreateBase()
         {
             SaveFileDialog saveFileDialog = new SaveFileDialog();
             saveFileDialog.ShowDialog();
             if (!_connections.PcBase.Create(saveFileDialog.FileName))
             {
                 _connections.PcBase.Path = null;
             }
         }*/
    }
}
