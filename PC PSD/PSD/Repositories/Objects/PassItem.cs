using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace PSD
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

    }
}
