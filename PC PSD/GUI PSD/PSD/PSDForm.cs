using System;
using System.Linq;
using System.Windows.Forms;
using PsdBasesSetter;
using PsdBasesSetter.Repositories;
using PsdBasesSetter.Repositories.Objects;
using PSD.Locales;

namespace PSD
{
    public partial class PSDForm : Form
    {
        private DataConnections _connections;
        private DateTime _lastChanges;

        private PasswordList _passwords => _connections.Passwords ?? new PasswordList();

        public PSDForm(DataConnections dataConnections)
        {
            InitializeComponent();
            _connections = dataConnections;
        }

        private void PSDForm_Load(object sender, EventArgs e)
        {
            if (_connections.PcBase == null)
            {
                MessageBox.Show(Localization.InputDataError);
                this.Close();
                return;
            }
            UpdateLabels();
            RefillPasswordsList();
        }



        private void UpdateLabels()
        {
            lblBasePathDesc.Visible = lblBasePath.Visible = _connections.PcBase != null;
            lblBasePath.Text = _connections.PcBase?.Path;

            lblAndroidPathDesc.Visible = lblAndroidBasePath.Visible = _connections.PhoneBase != null;
            lblAndroidBasePath.Text = _connections.PhoneBase?.Path;

            lblPsdConnectionDesc.Visible = lblPsdConnected.Visible = _connections.PsdBase != null;
            lblPsdConnected.Text = _connections.PsdBase?.Name;
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

            if (string.IsNullOrWhiteSpace(pass.Login) && pass.EnterWithLogin)
            {
                MessageBox.Show("No login set to \"Enter with login\"");
                return false;
            }

            /*  if (string.IsNullOrWhiteSpace(pass.Pass))
              {
                  MessageBox.Show("Password is empty");
                  return false;
              }
              */

            /* if (string.IsNullOrWhiteSpace(pass.Description))
             {
                 MessageBox.Show("Description is empty");
                 return false;
             }*/
            return true;
        }

        private bool Contains(PassItem pass)
        {
            return _connections.PcBase.Base.Passwords.Any(a => a.Key == pass.Id && a.Value != pass);
        }




        private void btnAddPass_Click(object sender, EventArgs e)
        {
            var selectedIndex = lstPasses.SelectedIndex;

            var newPassword = new PassItem();
            
            if (!EditPassword(newPassword))
                return;

            _passwords.AddPass(newPassword);
            if (selectedIndex != -1 && selectedIndex != lstPasses.Items.Count - 1)
            {
                while (newPassword.Id > ((PassItem)lstPasses.SelectedItem).Id)
                {
                    _passwords.MoveUp(newPassword);
                }
                _passwords.MoveDown(newPassword);
            }
            RegisterChange();
            RefillPasswordsList();
        }


        private void RegisterChange()
        {
            _lastChanges = DateTime.Now;
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
            MessageBox.Show("All bases successfully saved");
        }




        private void SaveAll()
        {
            if (_connections.WriteAll() != WriteAllResult.Success)
            {
                MessageBox.Show(Localization.UpdatingAllError);
            }
        }

        private void lstViewPasswords_DoubleClick(object sender, EventArgs e)
        {
            var selectedPass = (PassItem)lstPasses.SelectedItem;
            if (selectedPass == null)
                return;

            var backup = selectedPass.GetCopy();
            if (!EditPassword(selectedPass))
                selectedPass.RestoreCopy(backup);
            else
                RegisterChange();
            RefillPasswordsList();
        }

        private void btnRemovePass_Click(object sender, EventArgs e)
        {
            if (lstPasses.SelectedItem != null)
            {
                _passwords.RemovePass((ushort)((PassItem)lstPasses.SelectedItem).Id);
                RefillPasswordsList();
            }
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            _passwords.FillEmptySpaces();
            RefillPasswordsList();
        }





        private void RefillPasswordsList()
        {
            var selectedItem = lstPasses.SelectedItem;
            lstPasses.Items.Clear();
            foreach (var keyValuePair in _passwords)
            {
                lstPasses.Items.Add(keyValuePair.Value);
            }
            lstPasses.SelectedItem = selectedItem;

        }




        private void btnMoveUp_Click(object sender, EventArgs e)
        {
            var selectedPass = (PassItem)lstPasses.SelectedItem;
            if (selectedPass == null)
                return;
            if (_passwords.MoveUp(selectedPass))
            {
                RegisterChange();
                RefillPasswordsList();
            }
        }

        private void btnMoveDown_Click(object sender, EventArgs e)
        {
            var selectedPass = (PassItem)lstPasses.SelectedItem;
            if (selectedPass == null)
                return;
            if (_passwords.MoveDown(selectedPass))
            {
                RegisterChange();
                RefillPasswordsList();
            }
        }




        private bool ExitCheck()
        {
            //check if not saved
            if (_connections.LastUpdate < _lastChanges)
                return MessageBox.Show(
                    Localization.ExitQuestion,
                    Localization.ExitQuestionFormText,
                    MessageBoxButtons.YesNo)
                       == DialogResult.Yes;
            return true;
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
            if (_connections.PcBase == null)
            {
                MessageBox.Show(Localization.NotConnectedWarning);
                return;
            }
            _connections.PcBase.WriteChanges();
        }

        private void savePhoneMenuItem_Click(object sender, EventArgs e)
        {
            if (_connections.PhoneBase == null)
            {
                MessageBox.Show(Localization.NotConnectedWarning);
                return;
            }
            _connections.PhoneBase.WriteChanges();
        }

        private void saveToPsdMenuItem_Click(object sender, EventArgs e)
        {
            if (_connections.PsdBase == null)
            {
                MessageBox.Show(Localization.NotConnectedWarning);
                return;
            }

            if (!_connections.PsdBase.WriteChanges())
            {
                MessageBox.Show(Localization.PsdUpdateError);
            }
        }

        private void newPCBaseMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void saveAsPcMenuItem_Click(object sender, EventArgs e)
        {
            if (!SaveAs(_connections.PcBase))
                MessageBox.Show("Errors saving as new file");
        }

        private void saveAsPhoneMenuItem_Click(object sender, EventArgs e)
        {
            if (!SaveAs(_connections.PhoneBase))
                MessageBox.Show("Errors saving as new file");
        }

        private bool SaveAs(FileRepository fRepository)
        {
            var oldPath = fRepository.Path;
            var dialog = new SaveFileDialog();
            dialog.ShowDialog();
            var newFilePath = dialog.FileName;
            if (!fRepository.SaveAs(newFilePath))
            {
                fRepository.Path = oldPath;
                return false;
            }
            return true;
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
