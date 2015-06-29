using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;
using Newtonsoft.Json.Linq;

namespace PSD
{
    [Serializable]

    public class PasswordsList : BindingList<PassItem>
    {
        public PasswordsList()
        {

        }

        public void Sort()
        {
            Items.
            List<PassItem> items = this.Items as List<PassItem>;
            items.Sort();
        }
    }
}
