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
            ushort newId;
            newId = item.Id ?? FindMinEmptyId();

            if (ContainsKey(newId))
                return false;

            Add(newId, item);
            item.Id = newId;//changing after adding because if there will be any error than we wont break the item
            return true;
        }

        private ushort FindMinEmptyId()
        {
            return 4;//lol. Debug
        }


    }
}
