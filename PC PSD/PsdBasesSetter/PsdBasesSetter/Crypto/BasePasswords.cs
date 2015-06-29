using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;

namespace PsdBasesSetter.Crypto
{
    public class BasePasswords : INotifyPropertyChanged
    {
        public byte[] PhonePassword { get; private set; }
        public byte[] BasePassword { get; private set; }

        private const int TimesByteNum = 0;

        public BasePasswords(string pass)
        {
            BasePassword = GenerateUserPassword(Encoding.ASCII.GetBytes(pass));
            PhonePassword = GeneratePhonePassword(Encoding.ASCII.GetBytes(pass));
        }


        private byte[] GenerateUserPassword(byte[] pass)
        {
            SHA256 mySha256 = SHA256Managed.Create();
            return mySha256.ComputeHash(pass);
        }


        private byte[] GeneratePhonePassword(byte[] pass)
        {
            SHA256 mySha256 = SHA256Managed.Create();
            byte generateTimes = mySha256.ComputeHash(pass)[TimesByteNum];
            if (generateTimes <= 1)
                generateTimes++;

            byte[] result = pass;
            for (byte i = 0; i < generateTimes; i++)
            {
                result = mySha256.ComputeHash(result);
            }
            return result;
        }

        public event PropertyChangedEventHandler PropertyChanged;
        
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
