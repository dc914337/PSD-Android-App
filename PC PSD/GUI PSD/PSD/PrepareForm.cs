using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using PsdBasesSetter;
using PsdBasesSetter.Device.Hid;
using PsdBasesSetter.Repositories;
using PSD.Locales;
using PSD.Properties;

namespace PSD
{
    public partial class PrepareForm : Form
    {
        public DataConnections DataConnections { get; set; } = new DataConnections();
        public bool StartApp { get; private set; } = false;


        public PrepareForm()
        {
            InitializeComponent();
        }


        private void btnConnectPsd_Click(object sender, EventArgs e)
        {
            TryConnectPSDBase();
            UpdateLables();
        }


        private void btnSet_Click(object sender, EventArgs e)
        {
            if (txtPassword.Enabled)
            {
                btnSet.Text = Localization.btnUnsetText;
                DataConnections.UserPass = txtPassword.Text;
                ReinitPsds();
            }
            else
            {
                btnSet.Text = Localization.btnSetText;
            }
            SwitchEnabled();
        }

        private bool ReinitPsds()
        {
            var finder = new PSDFinder();
            var psds = finder.FindConnectedPsds();
            cmbPsds.Items.Clear();
            cmbPsds.Items.AddRange(psds);
            if (psds.Any())
                cmbPsds.SelectedIndex = 0;
            return psds.Any();
        }


        private void btnSelectStorageFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectPcBase(fileDialog.FileName);
            UpdateLables();
        }


        private void btnSelectPhoneFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectAndroidBase(fileDialog.FileName);
            UpdateLables();
        }

        private void TryConnectPcBase(string path)
        {
            if (!DataConnections.TrySetPcBase(path))
                MessageBox.Show(Localization.CantLoadFileString);
        }

        private void TryConnectAndroidBase(string path)
        {
            if (!DataConnections.TrySetPhoneBase(path))
                MessageBox.Show(Localization.CantLoadFileString);

        }

        private void TryConnectPSDBase()
        {
            switch (DataConnections.TrySetPsdBase((PSDDevice)cmbPsds.SelectedItem))
            {
                case PSDRepository.SetPsdResult.NotConnected:
                    MessageBox.Show(Localization.PsdConnectionError);
                    break;
                case PSDRepository.SetPsdResult.WrongPassword:
                    MessageBox.Show(Localization.WrongPasswordPSD);
                    FlushPassword();
                    break;
            }
        }



        private void FlushPassword()
        {
            if ( MessageBox.Show( "Do you want to set new password on PSD?", "Reset PSD password", MessageBoxButtons.YesNo )
                 == DialogResult.Yes )
            {
                DataConnections.PsdBase.Reset();
            }
        }


        private void btnCreateStorageFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.ShowDialog();
            if (!DataConnections.TryCreateAndSetPcBase(saveFileDialog.FileName))
            {
                MessageBox.Show(Localization.CantLoadFileString);
            }
            UpdateLables();
        }



        private void btnCreatePhoneFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.ShowDialog();
            if (!DataConnections.TryCreateAndSetPhoneBase(saveFileDialog.FileName))
            {
                MessageBox.Show(Localization.CantLoadFileString);
            }
            UpdateLables();
        }

        private void UpdateLables()
        {
            lblBasePath.Text = DataConnections.PcBase?.Path;
            lblAndroidPath.Text = DataConnections.PhoneBase?.Path;
            lblConnectedPsd.Text = DataConnections.PsdBase?.Name;
        }



        private void SwitchEnabled()
        {
            txtPassword.Enabled = !txtPassword.Enabled;

            btnSelectPhoneFile.Enabled = !btnSelectPhoneFile.Enabled;
            btnCreatePhoneFile.Enabled = !btnCreatePhoneFile.Enabled;

            btnCreateStorageFile.Enabled = !btnCreateStorageFile.Enabled;
            btnSelectStorageFile.Enabled = !btnSelectStorageFile.Enabled;

            cmbPsds.Enabled = !cmbPsds.Enabled;
            btnConnectPsd.Enabled = !btnConnectPsd.Enabled;

            btnStart.Enabled = !btnStart.Enabled;
        }



        private void btnStart_Click(object sender, EventArgs e)
        {
            if (DataConnections.PcBase == null)
                MessageBox.Show(Localization.StorageFileNotSelectedError);
            else
            {
                this.Close();
                StartApp = true;
            }
        }

        private void btnRefresh_Click(object sender, EventArgs e)
        {
            if (!ReinitPsds())
                MessageBox.Show(Localization.NoPSDsError);
        }
    }
}
