using System;
using PsdBasesSetter.Crypto;
using PsdBasesSetter.Device.Hid;
using PsdBasesSetter.Repositories;
using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter
{
    public class DataConnections
    {
        private const int BtKeyLength = 32;
        private const int HBtKeyLength = 32;

        public FileRepository PcBase { get; set; }
        public FileRepository PhoneBase { get; private set; }
        public PSDRepository PsdBase { get; private set; }

        public String UserPass
        {
            set
            {
                _userPasses = new BasePasswords(value);
            }
        }

        private BasePasswords _userPasses;

        public PasswordList Passwords => PcBase.Base.Passwords;


        public bool TryCreateAndSetPcBase(String path)
        {
            if (_userPasses == null)
                return false;

            var newPcBase = new FileRepository(_userPasses.BasePassword);
            var connectResult = newPcBase.Create(path);
            if (!newPcBase.Create(path))
                return false;
            return TrySetPcBase(path);
        }


        public bool TrySetPcBase(String path)
        {
            if (_userPasses == null)
                return false;

            var newPcBase = new FileRepository(_userPasses.BasePassword);
            var connectResult = newPcBase.Connect(path);
            if (connectResult == ConnectResult.WrongPath)
                return false;

            if (connectResult == ConnectResult.Success)
                PcBase = newPcBase;

            return connectResult == ConnectResult.Success;
        }

        public bool TryCreateAndSetPhoneBase(String path)
        {
            if (_userPasses == null)
                return false;

            var newPhoneBase = new FileRepository(_userPasses.PhonePassword);
            var connectResult = newPhoneBase.Connect(path);
            if (!newPhoneBase.Create(path))
                return false;
            return TrySetPhoneBase(path);
        }

        public bool TrySetPhoneBase(String path)
        {
            if (_userPasses == null)
                return false;

            var newPhoneBase = new FileRepository(_userPasses.PhonePassword);
            var connectResult = newPhoneBase.Connect(path);
            if (connectResult == ConnectResult.WrongPath)
                return false;

            if (connectResult == ConnectResult.Success)
                PhoneBase = newPhoneBase;

            return connectResult == ConnectResult.Success;
        }

        public bool TrySetPsdBase(PSDDevice newDevice)
        {
            if (_userPasses == null)
                return false;

            var newPsdBase = new PSDRepository(_userPasses.PsdLoginPass);
            if (!newPsdBase.Connect(newDevice))
                return false;
            PsdBase = newPsdBase;
            return true;
        }


        public WriteAllResult WriteAll()
        {
            UpdateAll();

            if (PcBase != null && !PcBase.WriteChanges())
                return WriteAllResult.FailedPc;

            if (PhoneBase != null && !PhoneBase.WriteChanges())
                return WriteAllResult.FailedPhone;

            if (PsdBase != null && !PsdBase.WriteChanges())
                return WriteAllResult.FailedPsd;

            return WriteAllResult.Success;
        }


        private void UpdateAll()
        {
            byte[] hBtKEy = KeyGenerator.GenerateByteKey(HBtKeyLength);
            byte[] btKey = KeyGenerator.GenerateByteKey(BtKeyLength);

            DividedList dividedList = null;
            if (PhoneBase == null)
            {
                dividedList = new DividedList(new PasswordList());
            }
            else
            {
                dividedList = new DividedList(PcBase.Base.Passwords);
            }

            if (PhoneBase != null)
            {
                PhoneBase.Base.Passwords = dividedList?.Part1List;
                PhoneBase.Base.BTKey = btKey;
                PhoneBase.Base.HBTKey = hBtKEy;
            }

            if (PsdBase != null)
            {
                PsdBase.Base.Passwords = dividedList?.Part2List;
                PsdBase.Base.BTKey = btKey;
                PsdBase.Base.HBTKey = hBtKEy;
            }
        }
    }


    public enum WriteAllResult
    {
        Success,
        FailedPc,
        FailedPhone,
        FailedPsd
    }
}
