using System;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using Newtonsoft.Json;
using PsdBasesSetter.Repositories.Serializers;

namespace PsdBasesSetter.Repositories.Objects
{
    [Serializable]
    [DataContract]
    public class PassItem
    {
        private const char SEPARATOR = '\t';

        public PassItem()
        {
        }


        public PassItem(string title, byte[] password)
        {
            Id = null;
            Title = title;
            Login = null;
            EnterWithLogin = false;
            Pass = password;
            Description = null;
        }

        public PassItem(string title, string login, bool enterWithLogin, byte[] password, string description)
        {
            Id = null;
            Title = title;
            Login = login;
            EnterWithLogin = enterWithLogin;
            Pass = password;
            Description = description;
        }

        public PassItem(ushort? index, string title, string login, bool enterWithLogin, byte[] password, string description)
        {
            Id = index;
            Title = title;
            Login = login;
            EnterWithLogin = enterWithLogin;
            Pass = password;
            Description = description;
        }


        public byte[] GetBytes()
        {
            if (EnterWithLogin)
                return Encoding.UTF8.GetBytes(Login).Concat(GetBytesFromChar(SEPARATOR)).Concat(Pass).ToArray();

            return Pass;
        }

        private static byte[] GetBytesFromChar(char ch)
        {
            return Encoding.UTF8.GetBytes(
                new char[]
                {
                    ch
                });
        }


        [DataMember]
        public ushort? Id { get; set; }

        [DataMember]
        public string Title { get; set; } = String.Empty;

        [DataMember]
        public string Login { get; set; } = String.Empty;

        [DataMember]
        public bool EnterWithLogin { get; set; } = false;

        [DataMember]
        [JsonConverter(typeof(ByteArrayConverter))]
        public byte[] Pass { get; set; } = new byte[0];

        [DataMember]
        public string Description { get; set; } = String.Empty;

        public PassItem GetCopy()
        {
            return new PassItem(Id, Title, Login, EnterWithLogin, Pass, Description);
        }

        public override string ToString()
        {
            return Title;
        }

        public void RestoreCopy(PassItem backup)
        {
            this.Id = backup.Id;
            this.Title = backup.Title;
            this.Login = backup.Login;
            this.EnterWithLogin = backup.EnterWithLogin;
            this.Pass = backup.Pass;
            this.Description = backup.Description;
        }
    }
}
