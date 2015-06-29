using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace PSD.Repositories.Objects
{

    [Serializable]
    public class Base
    {
        [DataMember]
        public string BTKey { get; set; }
        [DataMember]
        public string HBTKey { get; set; }

        public PasswordsList Passwords { get; set; }
        
        public Base()
        {
            Passwords = new PasswordsList();
        }
    }
}
