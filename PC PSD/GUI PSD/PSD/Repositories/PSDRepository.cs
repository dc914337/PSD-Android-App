using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using PSD.Annotations;
using PSD.Device.Hid;

namespace PSD.Repositories
{
    public class PSDRepository : INotifyPropertyChanged, IRepository
    {
        public PSDDevice Psd;

        public DateTime LastUpdated { get; set; }
        private bool _connected = false;
        public bool Connected
        {
            get
            {
                return _connected;
            }
            set
            {
                _connected = value;
                OnPropertyChanged();
            }
        }

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
                OnPropertyChanged();
            }
        }

        public PSDRepository()
        {
            Connected = false;
        }

        public event PropertyChangedEventHandler PropertyChanged;

        [NotifyPropertyChangedInvocator]
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public bool Connect(PSDDevice psdDevice)
        {
            Connected = psdDevice.Connect();
            if (Connected)
            {
                Psd = psdDevice;
                Name = Psd.ToString();
            }
            else
                Psd = null;

            return Connected;
        }

        public WriteResult WriteChanges()
        {
            return WriteResult.Error;

        }
    }
}
