using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;

namespace PsdBasesSetter.Crypto
{
    public class BasePasswords
    {
        public byte[] PhonePassword { get; private set; }
        public byte[] BasePassword { get; private set; }
        public string PsdLoginPass { get; set; }

        private const int TimesByteNum = 0;

        public BasePasswords(string pass)
        {
            BasePassword = GenerateUserPassword(Encoding.ASCII.GetBytes(pass));
            PhonePassword = GeneratePhonePassword(Encoding.ASCII.GetBytes(pass));
            PsdLoginPass = //create some pass
        }


        private byte[] GenerateUserPassword(byte[] pass)
        {
            SHA256 mySha256 = SHA256.Create();
            return mySha256.ComputeHash(pass);
        }


        private byte[] GeneratePhonePassword(byte[] pass)
        {
            SHA256 mySha256 = SHA256.Create();
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


    }
}
