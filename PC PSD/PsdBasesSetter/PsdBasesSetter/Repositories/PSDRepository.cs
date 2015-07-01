using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using PsdBasesSetter.Device.Hid;
using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter.Repositories
{
    public class PSDRepository : IRepository
    {
        public PSDDevice Psd;
        public Base Base { get; private set; }

        public DateTime LastUpdated { get; set; }
        public byte[] LoginPass { get; set; }

        public String Name { get; set; }

        public PSDRepository(byte[] loginPass)
        {
            LoginPass = loginPass;
        }


        public bool Connect(PSDDevice psdDevice)
        {
            var connected = psdDevice.Connect();
            if (connected)
            {
                Psd = psdDevice;
                Name = Psd.ToString();
                Base = new Base();
            }
            else
                Psd = null;
            return connected;
        }

        public bool WriteChanges()
        {
            Psd.Login(LoginPass);
            Psd.WriteKeys(Base.BTKey, Base.HBTKey);
            var psdConverted = GetPreparedPasswords(Base.Passwords);
            int wrote = Psd.WritePasswords(psdConverted);

            return wrote == psdConverted.Count();
        }


        //We think that here passwords are without spaces(no empty indexes). But it's not. We need either resort passwords or allow empty indexes
        private List<byte[]> GetPreparedPasswords(PasswordList passes)
        {
            List<byte[]> psdConverted = new List<byte[]>();
            foreach (var pass in passes)
            {
                psdConverted.Add(Encoding.ASCII.GetBytes(pass.Value.Pass));
            }
            return psdConverted;
        }

        public bool Reset()
        {
            return Psd.Reset(LoginPass);
        }
    }
}
