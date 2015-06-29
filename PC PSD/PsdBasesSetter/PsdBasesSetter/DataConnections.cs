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

        public PasswordsList Passwords => PcBase.Base.Passwords;


        public SetResult TrySetPcBase(String path)
        {
            if (_userPasses == null)
                return SetResult.NoPassSet;

            var newPcBase = new FileRepository(_userPasses.BasePassword);
            if (!newPcBase.Connect(path))
                return SetResult.Failed;
            PcBase = newPcBase;
            return SetResult.Success;
        }

        public SetResult TrySetPhoneBase(String path)
        {
            if (_userPasses == null)
                return SetResult.NoPassSet;

            var newPhoneBase = new FileRepository(_userPasses.BasePassword);
            if (!newPhoneBase.Connect(path))
                return SetResult.Failed;
            PhoneBase = newPhoneBase;
            return SetResult.Success;
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
            if (!PcBase.WriteChanges())
                return WriteAllResult.FailedPC;

            if (!PhoneBase.WriteChanges())
                return WriteAllResult.FailedPhone;

            if (!PsdBase.WriteChanges())
                return WriteAllResult.FailedPsd;

            return WriteAllResult.Success;
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
