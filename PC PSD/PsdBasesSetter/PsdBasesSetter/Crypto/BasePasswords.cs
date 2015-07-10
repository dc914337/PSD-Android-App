using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;

namespace PsdBasesSetter.Crypto
{
    public class BasePasswords
    {

        private const String PsdPassSalt = "psd_salt_v1";
        private const String PCBasePassSalt = "pc_base_salt-v1";
        private const String PhoneBasePassSalt = "phone_base_salt-v1";

        private const byte MaxKeyBytes = 32;


        public byte[] PhonePassword { get; private set; }
        public byte[] BasePassword { get; private set; }
        public byte[] PsdLoginPass { get; set; }


        public BasePasswords(string pass)
        {
            BasePassword = GeneratePcPassword(pass);
            PhonePassword = GeneratePhonePassword(pass);
            PsdLoginPass = GeneratePsdPassword(pass);
        }


        private byte[] GeneratePcPassword(String pass)
        {
            return GenerateKey(pass, PCBasePassSalt);
        }
        private byte[] GeneratePhonePassword(String pass)
        {
            return GenerateKey(pass, PhoneBasePassSalt);
        }
        private byte[] GeneratePsdPassword(String pass)
        {
            return GenerateKey(pass, PsdPassSalt);
        }

        private static byte[] GenerateKey(String pass, String salt)
        {
            byte[] passBytes = Encoding.ASCII.GetBytes(pass);
            byte[] saltBytes = Encoding.ASCII.GetBytes(salt);

            SHA256 mySha256 = SHA256.Create();
            byte[] passHash = mySha256.ComputeHash(passBytes);


            byte[] saltedHash = mySha256.ComputeHash(ConcatArrays(passHash, saltBytes));
            byte[] resKey = new byte[MaxKeyBytes];

            Array.Copy(saltedHash, resKey, MaxKeyBytes); //i know that Sha256 will give me 32 bytes key. 

            return resKey;
        }

        private static byte[] ConcatArrays(byte[] arr1, byte[] arr2)
        {
            var result = new byte[arr1.Length + arr2.Length];
            arr1.CopyTo(result, 0);
            arr2.CopyTo(result, arr1.Length);
            return result;
        }




    }
}
