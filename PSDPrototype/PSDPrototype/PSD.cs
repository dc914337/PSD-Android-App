using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using PSDPrototype.Annotations;

namespace PSDPrototype
{
    class PSD : IUsbProtocolData, IBTConnectable
    {

        public BindingList<Key> PasswordList { get; private set; }
        public PSDCrypt Crypt { get; set; }

        public BTConnection btConnection;

        public Key UsbKey { get; set; }

        private ForeignPC pc { get; set; }


        public PSD()
        {
            UsbKey = new Key();
            Crypt = new PSDCrypt();
            PasswordList = new BindingList<Key>();
        }


        public void Clear()
        {
            Crypt.Clear();
            PasswordList.Clear();
        }

        public bool RecieveDataViaUsb(Key usbKey, Key btKey, Key hBtKey, List<Key> passwordsPart2)
        {
            if (UsbKey != usbKey) //Overloaded
                return false;
            Crypt.BtKey.KeyStr = btKey.KeyStr;
            Crypt.HBtKey.KeyStr = hBtKey.KeyStr;
            PasswordList.Clear();
            for (int i = 0; i < passwordsPart2.Count; i++)
            {
                PasswordList.Add(new Key(passwordsPart2[i].KeyBytes));
            }

            return true;
        }

        public void InsertIntoForeignPc(ForeignPC pc, Android android)
        {
            this.pc = pc;
            ConnectTo(android);
        }

        public void RecievePackageBytes(byte[] package)
        {
            bool correct;
            TempMessage message = Crypt.Decrypt(package, out correct);

            if (!correct)
            {
                MessageBox.Show("Wrong HMAC");
                return;
            }


            int index = BitConverter.ToUInt16(message.indexBytes.Reverse().ToArray(), 0);
            PasswordItem realPass = new PasswordItem(message.passP1.KeyBytes, PasswordList[index].KeyBytes);

            pc.EnterPassword(realPass.realPass);
        }



        public void RecieveBtConnection(IBTConnectable source, IBTConnectable partner)
        {
            throw new System.NotImplementedException();
        }

        public BTConnection ConnectTo(IBTConnectable dest)
        {
            btConnection = new BTConnection(this, dest, true);
            return btConnection;
        }
    }
}