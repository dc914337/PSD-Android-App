using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace PSDPrototype
{
    class MyPC : IUsbProtocolData
    {
        public Key UsbKey { get; set; }
        public Key BtKey { get; set; }
        public Key HBtKey { get; set; }

        public BindingList<PasswordItem> PasswordList;


        public MyPC()
        {
            UsbKey = new Key();
            BtKey = new Key();
            HBtKey = new Key();
            PasswordList = new BindingList<PasswordItem>() { new PasswordItem("password1"), new PasswordItem("PaSsWoRd1"), new PasswordItem("pasSword2"), new PasswordItem("paswwsfg"), new PasswordItem("'f3v3;l '432g'") };

        }

        public void AddPassword(string pass)
        {
            PasswordList.Add(new PasswordItem(pass));
        }

        public bool SendDataToPsdViaUsb(PSD reciever)
        {
            reciever.RecieveDataViaUsb(UsbKey, BtKey, HBtKey, GetPart2Passwords());
            return false;
        }

        public List<Key> GetPart1Passwords()
        {
            List<Key> passwordListPart1 = new List<Key>();
            for (int i = 0; i < PasswordList.Count; i++)
            {
                passwordListPart1.Add(new Key(PasswordList[i].part1));
            }
            return passwordListPart1;
        }

        public List<Key> GetPart2Passwords()
        {
            List<Key> passwordListPart2 = new List<Key>();
            for (int i = 0; i < PasswordList.Count; i++)
            {
                passwordListPart2.Add(new Key(PasswordList[i].part2));
            }
            return passwordListPart2;
        }

        public void SendInitDataToAndroid(Android reciever)
        {
            reciever.RecieveInitData(BtKey, HBtKey, GetPart1Passwords());
        }
    }
}