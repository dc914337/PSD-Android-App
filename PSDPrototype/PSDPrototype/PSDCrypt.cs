using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PSDPrototype
{
    class PSDCrypt
    {
        public const int KeyLength = 32;//HBtKey & BtKey:   32*8==256

        public const int IVLength = 16;



        public Key BtKey { get; set; }
        public Key HBtKey { get; set; }

        private static RandomNumberGenerator rnd = new RNGCryptoServiceProvider();

        public PSDCrypt()
        {
            BtKey = new Key();
            HBtKey = new Key();
        }

        public void Clear()
        {
            HBtKey.Clear();
            BtKey.Clear();
        }

        private TempMessage GeneratateTempMessage(int index, byte[] passP1Bytes)
        {
            TempMessage tempMessage = new TempMessage();
            //index
            byte[] indexBytes = new byte[TempMessage.sizeOfIndex];
            Array.Copy(BitConverter.GetBytes(index).Reverse().ToArray(), sizeof(int) - TempMessage.sizeOfIndex, indexBytes, 0, TempMessage.sizeOfIndex);
            tempMessage.indexBytes = indexBytes;
            //passP1
            tempMessage.passP1.KeyBytes = passP1Bytes;
            //next BT key
            byte[] nextBtKeyBytes = new byte[KeyLength];
            rnd.GetBytes(nextBtKeyBytes);
            tempMessage.NBTKey = new Key(nextBtKeyBytes);

            //next HBT key
            byte[] nextHBtKeyBytes = new byte[KeyLength];
            rnd.GetBytes(nextHBtKeyBytes);
            tempMessage.NHBTKey = new Key(nextHBtKeyBytes);


            return tempMessage;
        }


        private BTMessage GenerateMessage(byte[] tempMessageBytes)
        {
            byte[] IV;
            byte[] encryptedTempMessage = AESEncrypt(out IV, tempMessageBytes);

            BTMessage message =
                new BTMessage(IV, encryptedTempMessage,
                GetHMAC(HBtKey.KeyBytes, encryptedTempMessage));

            return message;
        }

        public byte[] Encrypt(int index, byte[] passP1Bytes)
        {
            TempMessage tempMessage = GeneratateTempMessage(index, passP1Bytes);

            byte[] tempMessageBytes = tempMessage.GetBytes();

            BTMessage message = GenerateMessage(tempMessageBytes);

            UpdateKeys(tempMessage);
            return message.GetBytes();
        }

        private void UpdateKeys(TempMessage tempMessage)
        {
            BtKey.KeyBytes = tempMessage.NBTKey.KeyBytes;
            HBtKey.KeyBytes = tempMessage.NHBTKey.KeyBytes;
        }



        private byte[] AESEncrypt(out byte[] IV, byte[] tempMessageBytes)
        {
            byte[] encrypted;
            using (Rijndael rijAlg = Rijndael.Create())
            {
                rijAlg.Key = BtKey.KeyBytes;
                IV = rijAlg.IV;//new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6 };// rijAlg.IV;
                ICryptoTransform encryptor = rijAlg.CreateEncryptor(rijAlg.Key, rijAlg.IV);
                using (MemoryStream msEncrypt = new MemoryStream())
                {
                    using (CryptoStream csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
                    {
                        csEncrypt.Write(tempMessageBytes, 0, TempMessage.Length);
                    }
                    encrypted = msEncrypt.ToArray();
                }
            }
            return encrypted;
        }


        public TempMessage Decrypt(byte[] fullMessage, out bool hmacCorrect)
        {
            BTMessage message = new BTMessage(fullMessage);
            byte[] countedHMAC = GetHMAC(HBtKey.KeyBytes, message.AESTempMessage);//not decrypted aes message!!!
            hmacCorrect = SecureArraysEquals(countedHMAC, message.HMACBytes);
            if (!hmacCorrect)
                return null;

            byte[] decryptedTempMessageBytes = AESDecrypt(message);

            TempMessage tempMessage = new TempMessage(decryptedTempMessageBytes);




            UpdateKeys(tempMessage);
            return tempMessage;
        }

        private byte[] AESDecrypt(BTMessage message)
        {
            byte[] decryptedTempMessageBytes = new byte[TempMessage.Length];
            using (Rijndael rijAlg = Rijndael.Create())
            {
                rijAlg.Key = BtKey.KeyBytes;
                rijAlg.IV = message.IV;

                ICryptoTransform decryptor = rijAlg.CreateDecryptor(rijAlg.Key, rijAlg.IV);
                using (MemoryStream msDecrypt = new MemoryStream(message.AESTempMessage))
                {
                    using (CryptoStream csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read))
                    {
                        csDecrypt.Read(decryptedTempMessageBytes, 0, TempMessage.Length);
                    }
                }
            }
            return decryptedTempMessageBytes;
        }


        private const byte allBitsSet = 255;// 1111 1111 (unsigned 8-bit)
        private bool SecureArraysEquals(byte[] countedHMACBytes, byte[] recievedHMACBytes)
        {
            uint diff = (uint)countedHMACBytes.Length ^ (uint)recievedHMACBytes.Length;
            for (int i = 0; i < countedHMACBytes.Length && i < recievedHMACBytes.Length; i++)
                diff |= (uint)(countedHMACBytes[i] ^ recievedHMACBytes[i]);
            return diff == 0;

        }


        private byte[] GetHMAC(byte[] key, byte[] messageBytes)
        {
            using (HMACSHA256 hmac = new HMACSHA256(key))
            {
                return hmac.ComputeHash(messageBytes);
            }
        }
    }
}
