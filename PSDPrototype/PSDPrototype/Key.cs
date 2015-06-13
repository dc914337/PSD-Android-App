using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Text;
using PSDPrototype.Annotations;

namespace PSDPrototype
{
    class Key : INotifyPropertyChanged
    {
        private string _KeyStr;

        private string _HEX;
        public string HEX
        {
            get
            {
                return _HEX;
            }
            set
            {
                if (_HEX != value)
                {
                    _HEX = value;
                    OnPropertyChanged("HEX");
                }
            }
        }

        public string KeyStr
        {
            get
            {
                return _KeyStr;
            }
            set
            {
                if (_KeyStr != value)
                {
                    _KeyStr = value;
                    _KeyBytes = Encoding.ASCII.GetBytes(value);
                    OnPropertyChanged("KeyStr");
                    HEX = BitConverter.ToString(KeyBytes);
                }
            }
        }

        private byte[] _KeyBytes;
        public byte[] KeyBytes
        {
            get
            {
                return _KeyBytes;
            }
            set
            {
                _KeyBytes = value;
                _KeyStr = Encoding.ASCII.GetString(value);
                OnPropertyChanged("KeyStr");
                HEX = BitConverter.ToString(KeyBytes);
            }
        }


        public Key()
        {
            KeyStr = String.Empty;
        }

        public Key(string keyStr)
        {
            KeyStr = keyStr;
        }
        public Key(byte[] keyByteses)
        {
            KeyBytes = keyByteses;
        }

        public void Clear()
        {
            KeyStr = String.Empty;
        }



        public static bool operator ==(Key c1, Key c2)
        {
            return c1.KeyStr == c2.KeyStr;
        }

        public static bool operator !=(Key c1, Key c2)
        {
            return !(c1.KeyStr == c2.KeyStr);
        }


        public override string ToString()
        {
            return KeyStr;
        }


        public event PropertyChangedEventHandler PropertyChanged;

        [NotifyPropertyChangedInvocator]
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}