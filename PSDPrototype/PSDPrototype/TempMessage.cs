using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSDPrototype
{
    class TempMessage
    {
        public const int Length = 192;
        public const int passPartLength = 126;
        public const int sizeOfIndex = 2;

        public byte[] indexBytes;
        public Key passP1;
        public Key NBTKey;
        public Key NHBTKey;

        public TempMessage()
        {
            indexBytes = new byte[sizeOfIndex];
            passP1 = new Key();
            NBTKey = new Key();
            NHBTKey = new Key();
        }

        public TempMessage(byte[] tempMessageBytes)
        {
            int offset = 0;
            indexBytes = new byte[sizeOfIndex];

            byte[] passP1Bytes = new byte[passPartLength];
            byte[] NBTKeyBytes = new byte[PSDCrypt.KeyLength];
            byte[] NHBTKeyBytes = new byte[PSDCrypt.KeyLength];

            Array.Copy(tempMessageBytes, offset, indexBytes, 0, indexBytes.Length);
            offset += indexBytes.Length;

            Array.Copy(tempMessageBytes, offset, passP1Bytes, 0, passP1Bytes.Length);
            offset += passP1Bytes.Length;

            Array.Copy(tempMessageBytes, offset, NBTKeyBytes, 0, NBTKeyBytes.Length);
            offset += NBTKeyBytes.Length;

            Array.Copy(tempMessageBytes, offset, NHBTKeyBytes, 0, NHBTKeyBytes.Length);


            passP1 = new Key(passP1Bytes);
            NBTKey = new Key(NBTKeyBytes);
            NHBTKey = new Key(NHBTKeyBytes);

        }


        public byte[] GetBytes()
        {
            byte[] tempMessage = new byte[Length];
            int offset = 0;
            indexBytes.CopyTo(tempMessage, offset);
            offset += indexBytes.Length;
            passP1.KeyBytes.CopyTo(tempMessage, offset);
            offset += passP1.KeyBytes.Length;
            NBTKey.KeyBytes.CopyTo(tempMessage, offset);
            offset += NBTKey.KeyBytes.Length;
            NHBTKey.KeyBytes.CopyTo(tempMessage, offset);
            return tempMessage;
        }
    }
}
