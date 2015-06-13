using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using PSDPrototype.Annotations;

namespace PSDPrototype
{
    public class BTConnection : INotifyPropertyChanged
    {
        private const String openedConnection = "Bluetooth connection is established";
        private const String closedConnection = "Bluetooth connection is closed";

        public IBTConnectable partner;

        private string _statusStr;
        public string StatusStr
        {
            get
            {
                return _statusStr;
            }
            set
            {
                if (_statusStr != value)
                {
                    _statusStr = value;
                    OnPropertyChanged("StatusStr");
                }
            }
        }

        private bool _opened;
        private bool opened
        {
            get
            {
                return _opened;
            }
            set
            {
                if (_opened != value)
                {
                    _opened = value;
                    StatusStr = value ? openedConnection : closedConnection;
                }
            }
        }



        public BTConnection(IBTConnectable source, IBTConnectable partner, bool client)
        {
            this.partner = partner;
            if (client)
                partner.RecieveBtConnection(source, partner);
            opened = true;
        }


        public void SendPackageBytes(byte[] package)
        {
            partner.RecievePackageBytes(package);
        }


        public event PropertyChangedEventHandler PropertyChanged;

        [NotifyPropertyChangedInvocator]
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
