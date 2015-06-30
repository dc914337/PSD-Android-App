using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace PsdBasesSetter.Crypto
{
    static class KeyGenerator
    {
        static RNGCryptoServiceProvider _rngCsp = new RNGCryptoServiceProvider();
        public static string GenerateStringKey(int bytesCount)
        {
            return Encoding.ASCII.GetString(GenerateByteKey(bytesCount));
        }

        public static byte[] GenerateByteKey(int bytesCount)
        {
            byte[] array = new byte[bytesCount];
            _rngCsp.GetNonZeroBytes(array);
            return array;
        }
    }
}
