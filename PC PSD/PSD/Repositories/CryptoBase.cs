using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace PSD.Repositories
{
    class CryptoBase
    {
        public const int IVLength = 16;
        private byte[] key;

        public CryptoBase(byte[] key)
        {
            this.key = key;
        }

        public bool DecryptAll(byte[] raw, out string dataStr)
        {
            byte[] IV = new byte[IVLength];
            int encryptedLength = raw.Length - IVLength;
            byte[] encrypted = new byte[encryptedLength];
            Array.Copy(raw, IV, IVLength);
            Array.Copy(raw, IVLength, encrypted, 0, encryptedLength);
            byte[] resBytes;
            if (!AESDecrypt(IV, encrypted, out resBytes))
            {
                dataStr = null;
                return false;
            }
            dataStr = Encoding.ASCII.GetString(resBytes);
            return true;
        }

        public byte[] EncryptAll(string raw)
        {
            byte[] IV;
            byte[] encrypted = AESEncrypt(out IV, Encoding.ASCII.GetBytes(raw));
            byte[] result = new byte[IV.Length + encrypted.Length];
            IV.CopyTo(result, 0);
            encrypted.CopyTo(result, IVLength);
            return result;
        }

        private byte[] AESEncrypt(out byte[] IV, byte[] raw)
        {
            byte[] encrypted;
            using (Rijndael rijAlg = Rijndael.Create())
            {
                rijAlg.Key = key;
                IV = rijAlg.IV;
                ICryptoTransform encryptor = rijAlg.CreateEncryptor(rijAlg.Key, rijAlg.IV);
                using (MemoryStream msEncrypt = new MemoryStream())
                {
                    using (CryptoStream csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
                    {
                        csEncrypt.Write(raw, 0, raw.Length);
                    }
                    encrypted = msEncrypt.ToArray();
                }
            }
            return encrypted;
        }

        private bool AESDecrypt(byte[] IV, byte[] encrypted, out byte[] resBytes)
        {
            byte[] decryptedTempMessageBytes = new byte[encrypted.Length];

            using (Rijndael rijAlg = Rijndael.Create())
            {
                rijAlg.Key = key;
                rijAlg.IV = IV;

                ICryptoTransform decryptor = rijAlg.CreateDecryptor(rijAlg.Key, rijAlg.IV);
                using (MemoryStream msDecrypt = new MemoryStream(encrypted))
                {
                    try
                    {
                        using (CryptoStream csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read))
                        { csDecrypt.Read(decryptedTempMessageBytes, 0, decryptedTempMessageBytes.Length); }
                    }
                    catch (CryptographicException ex)
                    {
                        resBytes = null;
                        return false;
                    }
                }
            }
            resBytes = decryptedTempMessageBytes;
            return true;
        }
    }
}
