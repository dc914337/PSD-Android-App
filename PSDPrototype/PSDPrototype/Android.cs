using System.Collections.Generic;
using System.ComponentModel;

namespace PSDPrototype
{
    class Android : IBTConnectable
    {
        public PSDCrypt Crypt { get; set; }
        public BindingList<Key> PasswordList { get; set; }
        public BTConnection btConnection;


        public Android()
        {
            Crypt = new PSDCrypt();
            PasswordList = new BindingList<Key>();
        }


        public void Clear()
        {
            Crypt.Clear();
            PasswordList.Clear();
        }


        public void RecieveInitData(Key btKey, Key hBtKey, List<Key> passwordsPart1)
        {
            Crypt.BtKey.KeyStr = btKey.KeyStr;
            Crypt.HBtKey.KeyStr = hBtKey.KeyStr;

            for (int i = 0; i < passwordsPart1.Count; i++)
            {
                PasswordList.Add(new Key(passwordsPart1[i].KeyBytes));
            }
        }

        public void RecievePackageBytes(byte[] package)
        {
            throw new System.NotImplementedException();
        }

        public void RecieveBtConnection(IBTConnectable partner, IBTConnectable source)
        {
            btConnection = new BTConnection(source, partner, false);//android is a BT-server(but in our protocol it will be client)
        }


        public byte[] SendPasswordByIndex(int selectedIndex)
        {
            byte[] passBytes = PasswordList[selectedIndex].KeyBytes;

            byte[] encrypted = Crypt.Encrypt(selectedIndex, passBytes);
            btConnection.SendPackageBytes(encrypted);
            return encrypted;
        }
    }
}