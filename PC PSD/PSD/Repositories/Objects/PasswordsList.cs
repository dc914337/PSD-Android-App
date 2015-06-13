using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace PSD
{
    [Serializable]

    public class PasswordsList : BindingList<PassItem> { }
}
