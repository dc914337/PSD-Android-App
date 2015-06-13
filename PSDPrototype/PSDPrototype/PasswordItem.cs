using System;
using System.Text;
using System.Threading;
using System.Security.Cryptography;

namespace PSDPrototype
{
    class PasswordItem
    {
        public byte[] part1 = new byte[TempMessage.passPartLength];
        public byte[] part2 = new byte[TempMessage.passPartLength];

        private static RandomNumberGenerator rnd = new RNGCryptoServiceProvider();

        public readonly string realPass;
        public PasswordItem(String pass)
        {
            realPass = pass;

            byte[] part1Bytes = new byte[TempMessage.passPartLength];
            rnd.GetBytes(part1Bytes);
            Thread.Sleep(200);//i hate random and i want to sleep. so sleep will fix random instead of me

            byte[] realBytes = new byte[TempMessage.passPartLength];
            Encoding.ASCII.GetBytes(pass).CopyTo(realBytes, 0);

            byte[] part2Bytes = new byte[TempMessage.passPartLength];
            for (int i = 0; i < TempMessage.passPartLength; i++)
            {
                part2Bytes[i] = (byte)(part1Bytes[i] ^ realBytes[i]);
            }

            part1 = part1Bytes;
            part2 = part2Bytes;
        }


        public PasswordItem(byte[] part1Bytes, byte[] part2Bytes)
        {
            part1 = part1Bytes;
            part2 = part2Bytes;
            byte[] realPassBytes = new byte[part1Bytes.Length];
            for (int i = 0; i < TempMessage.passPartLength; i++)
            {
                realPassBytes[i] = (byte)(part1Bytes[i] ^ part2Bytes[i]);
            }
            realPass = Encoding.ASCII.GetString(realPassBytes);
        }

        public override string ToString()
        {
            return realPass;
        }
    }
}