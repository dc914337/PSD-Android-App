using System;
using PSD.Device.Hid;
using PSD.Repositories;

namespace PSD
{
    public class DataConnections
    {
        public FileRepository PcBase { get; private set; }
        public FileRepository PhoneBase { get; private set; }
        public PSDRepository PsdBase { get; private set; }
        public BasePasswords UserPasses { get; set; }

        public PasswordsList Passwords => PcBase.Base.Passwords;

        public DataConnections()
        {

        }

        public DataConnections(BasePasswords passes)
        {
            UserPasses = passes;
            PcBase = new FileRepository(passes.BasePassword);
            PhoneBase = new FileRepository(passes.PhonePassword);
            PsdBase = new PSDRepository();
        }


        public bool SetPCBase(String path)
        {
            PcBase = new FileRepository(UserPasses.BasePassword);
            bool success = PcBase.Connect(path);
            //update phone base and psd base

            return success;
        }

        public void DropPcBase()
        {
            PcBase = null;
        }

        public bool SetPhoneBase(String path)
        {
            if (PcBase == null && UserPasses == null)
                throw new Exception("You need to set PC base or specify passes first");
            PhoneBase = new FileRepository(UserPasses.PhonePassword);
            return PhoneBase.Connect(path);
        }
        public void DropPhoneBase()
        {
            PhoneBase = null;
        }

        public bool SetPsdDevice(PSDDevice device)
        {
            return false;
        }
        public void DropPsdBase()
        {
            PsdBase = null;
        }


        public bool UpdateInAllAvailableBases()
        {
            return UpdateIfConnected(PcBase) && UpdateIfConnected(PhoneBase) && UpdateIfConnected(PsdBase);
        }


        public bool UpdateIfConnected(IRepository repository)
        {
            return !(repository?.Connected == true && repository.WriteChanges() == WriteResult.Error);
        }

        //wrote later than registered changes
        public bool AllUpToDate(DateTime lastEdit)
        {
            var v1 = UpToDate(PcBase, lastEdit);
            var v2 = UpToDate(PhoneBase, lastEdit);
            var v3 = UpToDate(PsdBase, lastEdit);
            return v1 && v2 && v3;
        }

        public bool UpToDate(IRepository repository, DateTime lastEdit)
        {
            return repository?.Connected == false || repository?.LastUpdated >= lastEdit;
        }




    }

}
