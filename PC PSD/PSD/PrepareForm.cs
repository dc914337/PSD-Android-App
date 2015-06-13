using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PSD
{
    public partial class PrepareForm : Form
    {
        public DataConnections DataConnections { get; set; }
        Config Cfg { get; set; }

        public PrepareForm()
        {
            InitializeComponent();
        }

        private void PrepareForm_Load(object sender, EventArgs e)
        {
            Cfg = new Config();
        }

        private void txtPassword_TextChanged(object sender, EventArgs e)
        {

        }

        private void btnConnectPsd_Click(object sender, EventArgs e)
        {
            if (DataConnections.PsdBase.Connect((byte)nudCom.Value))
                Cfg.PsdCom = (byte)nudCom.Value;
        }

        private void btnSet_Click(object sender, EventArgs e)
        {
            if (txtPassword.Enabled)
            {
                btnSet.Text = "Unset";
                DataConnections = new DataConnections(new BasePasswords(txtPassword.Text));

                lblBasePath.DataBindings.Clear();
                lblBasePath.DataBindings.Add(new Binding("Text", DataConnections.PcBase, "Path"));


                lblAndroidPath.DataBindings.Clear();
                lblAndroidPath.DataBindings.Add(new Binding("Text", DataConnections.PhoneBase, "Path"));

                lblPsdConnection.DataBindings.Clear();
                lblPsdConnection.DataBindings.Add(new Binding("Text", DataConnections.PsdBase, "ComPort"));

                nudCom.Value = Cfg.PsdCom;


                TryConnectPcBase(Cfg.PcBasePath);
                TryConnectAndroidBase(Cfg.PhoneBasePath);
                TryConnectPSDBase(Cfg.PsdCom);
            }
            else
            {
                btnSet.Text = "Set";
            }
            SwitchEnabled();
        }



        private void btnSelectStorageFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectPcBase(fileDialog.FileName);
        }


        private void TryConnectPcBase(string path)
        {
            if (DataConnections.PcBase.Connect(path))
                Cfg.PcBasePath = DataConnections.PcBase.Path;
            else
                DataConnections.PcBase.Path = null;
        }

        private void TryConnectAndroidBase(string path)
        {
            if (DataConnections.PhoneBase.Connect(path))
                Cfg.PhoneBasePath = path;
            else
                DataConnections.PhoneBase.Path = null;

        }

        private void TryConnectPSDBase(byte comPort)
        {
            if (DataConnections.PsdBase.Connect(comPort))
                Cfg.PsdCom = comPort;
            else
                DataConnections.PsdBase.ComPort = 0;

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

        private void btnSelectPhoneFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectAndroidBase(fileDialog.FileName);
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

            nudCom.Enabled = !nudCom.Enabled;
            btnConnectPsd.Enabled = !btnConnectPsd.Enabled;

            btnStart.Enabled = !btnStart.Enabled;
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(DataConnections?.PcBase?.Path))
                MessageBox.Show("You must select at least storage file");
            else
                this.Close();
        }


    }
}
