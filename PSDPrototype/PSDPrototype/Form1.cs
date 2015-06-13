using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PSDPrototype
{
    public partial class Form1 : Form
    {
        private PSD psd;
        private Android android;
        private ForeignPC foreignPc;
        private MyPC myPc;


        public Form1()
        {
            InitializeComponent();
            psd = new PSD();
            android = new Android();
            foreignPc = new ForeignPC();
            myPc = new MyPC();
            BindRepresentation();
        }

        public void BindRepresentation()
        {
            ListPasswords.DataSource = myPc.PasswordList;

            Part2PasswordList.DataSource = psd.PasswordList;
            Part2PasswordList.DisplayMember = "HEX";

            Part1PasswordList.DataSource = android.PasswordList;
            Part1PasswordList.DisplayMember = "HEX";
            //psd bindings
            PSDBtKey.DataBindings.Add("Text", psd.Crypt.BtKey, "HEX");
            PSDUsbKey.DataBindings.Add("Text", psd.UsbKey, "HEX");
            PSDHBtKey.DataBindings.Add("Text", psd.Crypt.HBtKey, "HEX");
            //android
            AndroidBtKey.DataBindings.Add("Text", android.Crypt.BtKey, "HEX");
            AndroidHBtKey.DataBindings.Add("Text", android.Crypt.HBtKey, "HEX");
            //my PC
            PCUsbKey.DataBindings.Add("Text", myPc.UsbKey, "KeyStr");
            PCBtKey.DataBindings.Add("Text", myPc.BtKey, "KeyStr");
            PCHBtKey.DataBindings.Add("Text", myPc.HBtKey, "KeyStr");
            //foreign pc
            EnteredPassword.DataBindings.Add("Text", foreignPc.enteredKey, "KeyStr");
        }


        private void button2_Click(object sender, EventArgs e)
        {
            psd.Clear();
            psd.UsbKey.KeyStr = myPc.UsbKey.KeyStr;

        }


        private void LoadDataToPsd_Click(object sender, EventArgs e)
        {
            myPc.SendDataToPsdViaUsb(psd);
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            myPc.AddPassword(PCAddPassword.Text);
            ListPasswords.Refresh();
            PCAddPassword.Text = String.Empty;
        }

        private void PCUsbKey_TextChanged(object sender, EventArgs e)
        {
            myPc.UsbKey = new Key(((TextBox)sender).Text);
        }

        private void PCBtKey_TextChanged(object sender, EventArgs e)
        {
            myPc.BtKey = new Key(((TextBox)sender).Text);
        }

        private void PCHBtKey_TextChanged(object sender, EventArgs e)
        {
            myPc.HBtKey = new Key(((TextBox)sender).Text);
        }

        private void LoadDataToAndroid_Click(object sender, EventArgs e)
        {
            android.Clear();
            myPc.SendInitDataToAndroid(android);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            psd.InsertIntoForeignPc(foreignPc, android);
            label11.DataBindings.Add("Text", psd.btConnection, "StatusStr");
        }

        private void button5_Click(object sender, EventArgs e)
        {
            byte[] message = android.SendPasswordByIndex(Part1PasswordList.SelectedIndex);

            byte[] IV = new byte[16];
            Array.Copy(message, 0, IV, 0, 16);
            richTextBox1.Text = CreateArrInitCode(IV, "resIV");
            richTextBox2.Text = CreateArrInitCode(message, "resMsg");
            richTextBox3.Text = CreateArrInitCode(android.PasswordList[Part1PasswordList.SelectedIndex].KeyBytes, "passwordPart1");

            byte[] tempMessage = new byte[208];
            Array.Copy(message, 16, tempMessage, 0, tempMessage.Length);
            richTextBox4.Text = CreateArrInitCode(tempMessage, "tempMessage");
            label12.Text = Part1PasswordList.SelectedIndex.ToString();
            //MessageBox.Show(BitConverter.ToString(message));
        }

        //DEBUG
        private static string CreateArrInitCode(byte[] arr, string arrName)
        {
            string res = "byte[] " + arrName + " = new byte[]{";
            for (int i = 0; i < arr.Length; i++)
            {
                res += "(byte)" + arr[i];
                if (i < arr.Length - 1)
                {
                    res += ", ";
                }
            }
            res += "};";
            return res;
        }


        private void button6_Click(object sender, EventArgs e)
        {

        }

        private void button2_Click_1(object sender, EventArgs e)
        {
            PSDCrypt crypt = new PSDCrypt();
            byte[] toEncrypt = new byte[126];
            for (int i = 0; i < toEncrypt.Length; i++)
            {
                toEncrypt[i] = 3;
            }
            crypt.BtKey.KeyBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2 };
            crypt.HBtKey.KeyBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2 };
            byte[] r = crypt.Encrypt(12, toEncrypt);
            bool hmacCorrect;
            byte[] r2 = crypt.Decrypt(r, out hmacCorrect).passP1.KeyBytes;

            for (int i = 0; i < r.Length; i++)
            {
                Console.WriteLine(r[i]);
            }
        }
    }
}
