using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PSDPrototype
{
    class BTMessage
    {
        public const int Length = 256;
        public const int HMACLength = 32;
        public const int AESLength = 208;

        public byte[] IV;
        public byte[] AESTempMessage;
        public byte[] HMACBytes;

        public BTMessage(byte[] ivBytes, byte[] AESTempMessage, byte[] HMACBytes)
        {
            IV = ivBytes;
            this.AESTempMessage = AESTempMessage;
            this.HMACBytes = HMACBytes;
        }

        public BTMessage(byte[] fullMessage)
        {
            int offset = 0;
            IV = new byte[PSDCrypt.IVLength];
            AESTempMessage = new byte[AESLength];
            HMACBytes = new byte[PSDCrypt.KeyLength];

            Array.Copy(fullMessage, offset, IV, 0, PSDCrypt.IVLength);
            offset += PSDCrypt.IVLength;

            Array.Copy(fullMessage, offset, AESTempMessage, 0, BTMessage.AESLength);
            offset += BTMessage.AESLength;

            Array.Copy(fullMessage, offset, HMACBytes, 0, BTMessage.HMACLength);
        }


        public byte[] GetBytes()
        {
            byte[] message = new byte[Length];
            int offset = 0;
            IV.CopyTo(message, offset);
            offset += IV.Length;
            AESTempMessage.CopyTo(message, offset);
            offset += AESTempMessage.Length;
            HMACBytes.CopyTo(message, offset);
            return message;
        }
    }
}
