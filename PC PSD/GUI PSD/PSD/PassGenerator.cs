using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace PSD
{
    class PassGenerator
    {
        private char[] CHARS_AVAILABLE_IN_PASSWORD = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`~!@#$%^&*()-_=+[]{}\\|;:'\",<.>/?".ToCharArray();
        private RNGCryptoServiceProvider rng;

        public PassGenerator()
        {
            rng = new RNGCryptoServiceProvider();
        }

        public string GenerateStringPassword(int length)
        {
            StringBuilder resPass = new StringBuilder();
            for (int i = 0; i < length; i++)
            {
                resPass.Append(CHARS_AVAILABLE_IN_PASSWORD[GetCryptographicRandomNumber(0, CHARS_AVAILABLE_IN_PASSWORD.Length)]);
            }
            return resPass.ToString();
        }


        /*I took some code for this method from here: http://www.codeproject.com/Articles/2393/A-C-Password-Generator */
        /**/
        private int GetCryptographicRandomNumber(int lBound, int uBound)
        {
            uint urndnum;
            byte[] rndnum = new Byte[4];
            int period = uBound - lBound;
            rng.GetBytes(rndnum);
            urndnum = System.BitConverter.ToUInt32(rndnum, 0);
            return (int)(urndnum % period) + lBound;
        }

    }
}
