using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter.Crypto
{

    class DividedList
    {
        public const int MaxPassLength = 126;

        public PasswordList Part1List { get; set; } = new PasswordList();
        public PasswordList Part2List { get; set; } = new PasswordList();
        RNGCryptoServiceProvider _rngCsp = new RNGCryptoServiceProvider();

        public DividedList(PasswordList mainList)
        {
            foreach (var pass in mainList)
            {
                DivideAndAdd(pass.Value);
            }


            _rngCsp.Dispose();
        }


        private void DivideAndAdd(PassItem pass)
        {
            var passPart1Bytes = new byte[MaxPassLength];
            _rngCsp.GetBytes(passPart1Bytes);

            var realPassBytes = pass.Pass;

            var passPart2Bytes = XORArrays(passPart1Bytes, realPassBytes);

            if (!CheckCorrect(passPart1Bytes, passPart2Bytes, realPassBytes))
                throw new Exception("WTF? Xor worked funny..");

            var part1Pass = pass.GetCopy();
            part1Pass.Pass = passPart1Bytes;
            Part1List.Add(part1Pass.Id.Value, part1Pass);

            var part2Pass = pass.GetCopy();
            part2Pass.Pass = passPart1Bytes;
            Part2List.Add(part2Pass.Id.Value, part2Pass);
        }


        private bool CheckCorrect(byte[] part1, byte[] part2, byte[] realPass)
        {
            String origPass = Encoding.ASCII.GetString(realPass);
            String resPass = Encoding.ASCII.GetString(XORArrays(part1, part2));
            return origPass.Equals(resPass);
        }

        private byte[] XORArrays(byte[] arr1, byte[] arr2)
        {
            int length = Math.Max(arr1.Length, arr2.Length);
            byte[] resBytes = new byte[length];

            Array.Copy(arr1, 0, resBytes, 0, arr1.Length);

            for (int i = 0; i < length; i++)
            {
                byte selectedByte;
                if (i < arr2.Length)
                    selectedByte = arr2[i];
                else
                    selectedByte = 0;
                resBytes[i] = (byte)((byte)selectedByte ^ (byte)resBytes[i]);
            }

            return resBytes;
        }


    }

}
