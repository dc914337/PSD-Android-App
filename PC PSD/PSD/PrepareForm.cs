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
using PSD.Config;
using PSD.Device.Hid;
using PSD.Locales;
using PSD.Properties;

namespace PSD
{
    public partial class PrepareForm : Form
    {
        public DataConnections DataConnections { get; set; }


        public PrepareForm()
        {
            InitializeComponent();
        }

        private void PrepareForm_Load(object sender, EventArgs e)
        {
        }

        private void txtPassword_TextChanged(object sender, EventArgs e)
        {

        }

        private void btnConnectPsd_Click(object sender, EventArgs e)
        {
            /* if (DataConnections.PsdBase.Connect((byte)nudCom.Value))
                 _configSection.PsdCom = (byte)nudCom.Value;*/
        }


        private void btnSet_Click(object sender, EventArgs e)
        {
            if (txtPassword.Enabled)
            {
                ReinitPsds();

                btnSet.Text = Localization.btnUnsetText;
                DataConnections = new DataConnections(new BasePasswords(txtPassword.Text));

                lblBasePath.DataBindings.Clear();
                lblBasePath.DataBindings.Add(new Binding("Text", DataConnections.PcBase, "Path"));


                lblAndroidPath.DataBindings.Clear();
                lblAndroidPath.DataBindings.Add(new Binding("Text", DataConnections.PhoneBase, "Path"));

                lblPsdConnected.DataBindings.Clear();
                lblPsdConnected.DataBindings.Add(new Binding("Text", DataConnections.PsdBase, "ComPort"));

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
            {
                cmbPsds.SelectedIndex = 0;
                return true;
            }
            else
            {
                return false;
            }

        }


        private void btnSelectStorageFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectPcBase(fileDialog.FileName);
        }


        private void btnSelectPhoneFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectAndroidBase(fileDialog.FileName);
        }

        private void TryConnectPcBase(string path)
        {
            if (!DataConnections.PcBase.Connect(path))
            {
                DataConnections.PcBase.Path = null;
                MessageBox.Show(Localization.CantLoadFileString);
            }
        }

        private void TryConnectAndroidBase(string path)
        {
            if (!DataConnections.PhoneBase.Connect(path))
            {
                DataConnections.PhoneBase.Path = null;
                MessageBox.Show(Localization.CantLoadFileString);
            }
        }

        private void TryConnectPSDBase(byte comPort)
        {
            /* if (DataConnections.PsdBase.Connect(comPort))
                 _configSection.PsdCom = comPort;
             else
                 DataConnections.PsdBase.ComPort = 0;*/

        }



        private void btnCreateStorageFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.ShowDialog();
            if (!DataConnections.PcBase.Create(saveFileDialog.FileName))
            {
                DataConnections.PcBase.Path = null;
            }
        }



        private void btnCreatePhoneFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.ShowDialog();
            if (!DataConnections.PhoneBase.Create(saveFileDialog.FileName))
            {
                DataConnections.PhoneBase.Path = null;
            }
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
            if (string.IsNullOrWhiteSpace(DataConnections?.PcBase?.Path))
                MessageBox.Show(Localization.StorageFileNotSelectedError);
            else
                this.Close();
        }

        private void btnRefresh_Click(object sender, EventArgs e)
        {
            if (!ReinitPsds())
                MessageBox.Show(Localization.NoPSDsError);
        }
    }
}
