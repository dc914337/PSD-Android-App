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
                        currPass.Id.ToString(),
                        currPass.Title,
                        currPass.Login });
                lstViewPasswords.Items.Add(item);
            }
        }





        private bool CheckPassword(PassItem pass)
        {

            if (ContainsId(pass.Id))
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

        private bool ContainsId(ushort id)
        {
            return _connections.PcBase.Base.Passwords.Any(a => a.Id == id);
        }

        private PassItem GetFirstSelectedPassword()
        {
            return GetSelectedPasswords()?.FirstOrDefault();
        }


        private PassItem[] GetSelectedPasswords()
        {
            if (!_passwords.Any())
                return null;
            if (lstViewPasswords.SelectedIndices.Count <= 0)
                return null;
            PassItem[] selectedItems = new PassItem[lstViewPasswords.SelectedItems.Count];
            for (uint i = 0; i < selectedItems.Length; i++)
            {
                var passId = lstViewPasswords.SelectedItems[(int)i].Index;
                selectedItems[i] = _passwords.FirstOrDefault(a => a.Id == passId);
            }
            return selectedItems;
        }


        private void btnAddPass_Click(object sender, EventArgs e)
        {
            PassItem newPassword = new PassItem();
            do
            {
                var editPassForm = new EditPasswordForm(newPassword);
                editPassForm.ShowDialog();
                if (!editPassForm.Confirmed)
                    return; //was cancelled
            } while (!CheckPassword(newPassword));
            _passwords.Add(newPassword);
            RefillPasswordsList();
        }

        private void btnSaveAll_Click(object sender, EventArgs e)
        {
           
        }
    }
}
