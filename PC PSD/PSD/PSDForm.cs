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
        
        public PSDForm(DataConnections dataConnections)
        {
            InitializeComponent();
            _connections = dataConnections;
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
            FillPasswordsList();
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

        private void FillPasswordsList()
        {
            lstViewPasswords.Items.Clear();
            var passwords = _connections.PcBase.Base.Passwords;

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

        private bool selectingNextItem = false; //to not update list values while changing list item
        //changed index
        private void lstViewPasswords_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (_changingItem)
                return;

            selectingNextItem = true;
            if (lstViewPasswords.SelectedIndices.Count > 0)
            {
                var selectedIndex = lstViewPasswords.SelectedIndices[0];
                SetNewSelectedItem(selectedIndex);

            }
            selectingNextItem = false;
        }


        private void cbxShowPassword_CheckedChanged(object sender, EventArgs e)
        {
            txtPass.UseSystemPasswordChar = cbxShowPassword.Checked;
        }



        private void UpdateChangedItem()
        {
            if (lstViewPasswords.SelectedIndices.Count <= 0)
                return;

            _changingItem = true;
            var selectedIndex = lstViewPasswords.SelectedIndices[0];

            ListViewItem currItem = new ListViewItem(
                new string[] {
                        nudId.Value.ToString(),
                        txtTitle.Text,
                        txtLogin.Text });
            currItem.Selected = true;
            lstViewPasswords.Items[selectedIndex] = currItem;
            _changingItem = false;
        }

        //changed textboxes
        private void RepresentationChanged(object sender, EventArgs e)
        {
            if (selectingNextItem)
                return;
            UpdateChangedItem();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            _connections.UpdateInAllAvailableBases();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            var index = ((ushort)nudId.Value);
            var title = txtTitle.Text;
            var login = txtLogin.Text;
            var enterWithLogin = cbxEnterWithLogin.Checked;
            var pass = txtPass.Text;
            var desc = txtDescription.Text;

            if (!CheckFields())
                return;

            var addingItem = new PassItem(index, title, login, enterWithLogin, pass, desc);
            AddItemAndSelect(addingItem);
        }

        private void AddItemAndSelect(PassItem addingItem)
        {
            _connections.PcBase.Base.Passwords.Add(addingItem);
            var item = new ListViewItem(
                               new string[] {
                        addingItem.Id.ToString(),
                        addingItem.Title,
                        addingItem.Login });
            item.Selected = true;
            lstViewPasswords.Items.Add(item);

            int indexInList = lstViewPasswords.Items.IndexOf(item);

            SetNewSelectedItem(indexInList);
        }

        private bool CheckFields()
        {
            var index = ((ushort)nudId.Value);
            var title = txtTitle.Text;
            var login = txtLogin.Text;
            var pass = txtPass.Text;
            var desc = txtDescription.Text;
            if (ContainsId(index))
            {
                MessageBox.Show("Contains this id");
                return false;
            }

            if (string.IsNullOrWhiteSpace(title))
            {
                MessageBox.Show("Title is empty");
                return false;
            }

            if (string.IsNullOrWhiteSpace(login))
            {
                MessageBox.Show("Login is empty");
                return false;
            }

            if (string.IsNullOrWhiteSpace(pass))
            {
                MessageBox.Show("Password is empty");
                return false;
            }

            if (string.IsNullOrWhiteSpace(desc))
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

        private void toolStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

        }

        private void newPCBaseToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }
    }
}
