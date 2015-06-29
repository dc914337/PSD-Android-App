using System;
using System.Runtime.Serialization;

namespace PsdBasesSetter.Repositories.Objects
{
    [Serializable]
    [DataContract]
    public class PassItem
    {
        public PassItem()
        {
        }

        public PassItem(ushort index, string title, string login, bool enterWithLogin, string password, string description)
        {
            Id = index;
            Title = title;
            Login = login;
            EnterWithLogin = enterWithLogin;
            Pass = password;
            Description = description;
        }


        [DataMember]
        public ushort Id { get; set; }

        [DataMember]
        public string Title { get; set; }

        [DataMember]
        public string Login { get; set; }

        [DataMember]
        public bool EnterWithLogin { get; set; }

        [DataMember]
        public string Pass { get; set; }

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

        public void InitFromPass(PassItem backup)
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
