using System;
using System.Runtime.Serialization;
using Newtonsoft.Json;
using PsdBasesSetter.Repositories.Serializers;

namespace PsdBasesSetter.Repositories.Objects
{
    [Serializable]
    [DataContract]
    public class PassItem
    {
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


        [DataMember]
        public ushort? Id { get; set; }

        [DataMember]
        public string Title { get; set; }

        [DataMember]
        public string Login { get; set; }

        [DataMember]
        public bool EnterWithLogin { get; set; }

        [DataMember]
        [JsonConverter(typeof(ByteArrayConverter))]
        public byte[] Pass { get; set; }

        [DataMember]
        public string Description { get; set; }

        public PassItem GetCopy()
        {
            return new PassItem(Id, Title, Login, EnterWithLogin, Pass, Description);
        }

        public override string ToString()
        {
            return Title;
        }

        public void Copy(PassItem backup)
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
