using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using PsdBasesSetter.Device.Hid;
using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter.Repositories
{
    public class PSDRepository : IRepository
    {
        public PSDDevice Psd;
        public Base Base { get; private set; }

        public DateTime LastUpdated { get; set; }

        private String _name;

        public String Name
        {
            get
            {
                return _name;
            }
            set
            {
                _name = value;
            }
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
            return false;
        }
    }
}
