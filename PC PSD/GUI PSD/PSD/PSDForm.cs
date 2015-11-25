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
            UpdateList();
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

            if (string.IsNullOrWhiteSpace(pass.Login))
            {
                MessageBox.Show("Login is empty");
                return false;
            }

            /*  if (string.IsNullOrWhiteSpace(pass.Pass))
              {
                  MessageBox.Show("Password is empty");
                  return false;
              }
              */

            if (string.IsNullOrWhiteSpace(pass.Description))
            {
                MessageBox.Show("Description is empty");
                return false;
            }
            return true;
        }

        private bool Contains(PassItem pass)
        {
            return _connections.PcBase.Base.Passwords.Any(a => a.Key == pass.Id && a.Value != pass);
        }


        private int GetLastIndex()
        {
            /*
            if (!_passwords.Any())
                return -1;
            return _passwords.OrderBy(a => a.Id).Last().Id;*/
            return 0;
        }

        private void btnAddPass_Click(object sender, EventArgs e)
        {
            /*var lastIndex = (ushort)(GetLastIndex() + 1);
            PassItem newPassword = new PassItem
            {
                Id = lastIndex //can fail
            };


            if (!EditPassword(newPassword))
                return;
            RegisterChange();

            _passwords.Add(newPassword);
            UpdateList();*/

        }


        private void RegisterChange()
        {
            _lastChanges = DateTime.Now;
        }

        private void ReindexPasswords()
        {
            /* ushort newIndex = 0;
             foreach (var pass in _passwords.OrderBy(a => a.Id))
             {
                 pass.Id = newIndex++;
             }*/
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
            /*  ReindexPasswords();
              // RefillPasswordsList();

              if (!_connections.UpdateInAllAvailableBases())
              {
                  MessageBox.Show(Localization.UpdatingAllError);
              }*/
        }

        private void lstViewPasswords_DoubleClick(object sender, EventArgs e)
        {
            /*var selectedPass = GetFirstSelectedPassword();
            var backup = selectedPass.GetCopy();
            if (!EditPassword(selectedPass))
                selectedPass.InitFromPass(backup);
            UpdateList();*/
        }

        private void btnRemovePass_Click(object sender, EventArgs e)
        {
            /* foreach (var selectedPass in GetSelectedPasswords())
             {
                 //_passwords.Remove(selectedPass);
             }
             RegisterChange();
             UpdateList();*/
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            UpdateList();
        }

        private void UpdateList()
        {
            ReindexPasswords();
            FillPasswordsList();
        }



        private void FillPasswordsList()
        {
            lstPasses.Items.Clear();
            for (ushort i = 0; i < _passwords.Count; i++)
            {
                var currPass = _passwords[i];
               /* ListViewItem item = new ListViewItem(
                    new string[] {
                        currPass.Id.ToString(),
                        currPass.Title,
                        currPass.Login });*/
                lstPasses.Items.Add(currPass);
            }
        }




        private void btnMoveUp_Click(object sender, EventArgs e)
        {
            /* var selectedPass = GetFirstSelectedPassword();
             if (selectedPass == null)
                 return;*/

            /*var prevPass = _passwords.LastOrDefault(a => a.Id == selectedPass.Id - 1);//we suppose that array has no spaces
            if (prevPass == null)
                return;
            SwapItems(selectedPass, prevPass);
            RegisterChange();
            UpdateList();*/
        }

        private void btnMoveDown_Click(object sender, EventArgs e)
        {
            /* var selectedPass = GetFirstSelectedPassword();
             if (selectedPass == null)
                 return;*/
            /*var nextPass = _passwords.FirstOrDefault(a => a.Id == selectedPass.Id + 1);//we suppose that array has no spaces
            if (nextPass == null)
                return;
            SwapItems(selectedPass, nextPass);
            RegisterChange();
            UpdateList();*/
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
            /*if (_connections.AllUpToDate(_lastChanges))
                return true;

            return MessageBox.Show(
                Localization.ExitQuestion,
                Localization.ExitQuestionFormText,
                MessageBoxButtons.YesNo)
                   == DialogResult.Yes;*/
            return false;//stub
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
            /*   if (!_connections.(_connections.PcBase))
               {
                   MessageBox.Show(Localization.PsdUpdatingError);
               }*/
        }

        private void savePhoneMenuItem_Click(object sender, EventArgs e)
        {
            /* if (!_connections.PhoneBase.Connected)
                 MessageBox.Show(Localization.NotConnectedWarning);

             if (!_connections.UpdateIfConnected(_connections.PhoneBase))
             {
                 MessageBox.Show(Localization.PhoneUpdateError);
             }*/
        }

        private void saveToPsdMenuItem_Click(object sender, EventArgs e)
        {
            /* if (!_connections.PsdBase.Connected)
                 MessageBox.Show(Localization.NotConnectedWarning);

             if (!_connections.UpdateIfConnected(_connections.PsdBase))
             {
                 MessageBox.Show(Localization.PsdUpdateError);
             }*/
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
