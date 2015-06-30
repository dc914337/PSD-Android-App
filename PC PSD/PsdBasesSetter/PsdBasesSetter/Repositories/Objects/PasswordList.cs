using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace PsdBasesSetter.Repositories.Objects
{
    [Serializable]
    public class PasswordList : SortedDictionary<ushort, PassItem>
    {
        public bool AddPass(PassItem item)
        {
            if ( ContainsKey( item.Id ) )
                return false;
            Add(item.Id, item);
            return true;
        }



    }
}
