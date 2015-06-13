using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using PSD.Annotations;

namespace PSD
{
    public class PSDRepository : INotifyPropertyChanged
    {
        public bool Connected
        {
            get;
            set;
        }

        private byte _comPort;

        public PSDRepository()
        {
            Connected = false;
        }


        public byte ComPort
        {
            get
            {
                return _comPort;
            }
            set
            {
                _comPort = value;
                OnPropertyChanged();
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        [NotifyPropertyChangedInvocator]
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public bool Connect(byte psdCom)
        {
            if (psdCom == 0) //no com 0. it equals null
                return false;
            ComPort = psdCom;
            Connected = true;
            return true;
        }
    }
}
