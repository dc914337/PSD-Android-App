using System;
using System.Runtime.Serialization;

namespace PsdBasesSetter.Repositories.Objects
{

    [Serializable]
    public class Base
    {
        [DataMember]
        public string BTKey { get; set; }
        [DataMember]
        public string HBTKey { get; set; }

        public PasswordList Passwords { get; set; }
        
        public Base()
        {
            Passwords = new PasswordList();
        }
    }
}
