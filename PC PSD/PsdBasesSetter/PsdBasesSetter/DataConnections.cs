using System;
using PsdBasesSetter.Crypto;
using PsdBasesSetter.Device.Hid;
using PsdBasesSetter.Repositories;
using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter
{
    public class DataConnections
    {
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


        public bool TrySetPcBase(String path)
        {
            if (_userPasses == null)
                return false;

            var newPcBase = new FileRepository(_userPasses.BasePassword);
            var connectResult = newPcBase.Connect(path);
            if (connectResult == ConnectResult.WrongPath)
                if (!newPcBase.Create(path))
                    return false;

            if (connectResult == ConnectResult.Success)
                PcBase = newPcBase;

            return connectResult == ConnectResult.Success;
        }

        public bool TrySetPhoneBase(String path)
        {
            if (_userPasses == null)
                return false;

            var newPhoneBase = new FileRepository(_userPasses.PhonePassword);
            var connectResult = newPhoneBase.Connect(path);
            if (connectResult == ConnectResult.WrongPath)
                if (!newPhoneBase.Create(path))
                    return false;

            if (connectResult == ConnectResult.Success)
                PhoneBase = newPhoneBase;

            return connectResult == ConnectResult.Success;
        }

        public SetResult TrySetPsdBase(PSDDevice newDevice)
        {
            if (_userPasses == null)
                return SetResult.NoPassSet;

            var newPsdBase = new PSDRepository();
            if (!newPsdBase.Connect(newDevice))
                return SetResult.Failed;
            PsdBase = newPsdBase;
            return SetResult.Success;
        }


        public WriteAllResult WriteAll()
        {
            if (PcBase != null && !PcBase.WriteChanges())
                return WriteAllResult.FailedPC;

            UpdateAll();


            if (PhoneBase != null && !PhoneBase.WriteChanges())
                return WriteAllResult.FailedPhone;

            if (PsdBase != null && !PsdBase.WriteChanges())
                return WriteAllResult.FailedPsd;

            return WriteAllResult.Success;
        }


        private void UpdateAll()
        {
            //update data in lists in phone and psd
        }

    }







    public enum SetResult
    {
        Success,
        Failed,
        NoPassSet
    }

    public enum WriteAllResult
    {
        Success,
        FailedPC,
        FailedPhone,
        FailedPsd
    }
}
