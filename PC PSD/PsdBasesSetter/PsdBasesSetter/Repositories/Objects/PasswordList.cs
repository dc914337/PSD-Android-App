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

        public PassItem GetPassById(ushort id)
        {
            PassItem retItem;
            if (!TryGetValue(id, out retItem))
                return null;
            return retItem;
        }

        private ushort FindMinEmptyId()
        {
            ushort lastKey = 0;
            while (ContainsKey(lastKey))
            {
                lastKey++;
            }
            return lastKey;
        }

        public bool RemovePass(ushort id)
        {
            var result = this.Remove(id);
            //maybe move all elements to fill spaces
            return result;
        }


        public void SwapPasswords(PassItem a, PassItem b)
        {
            RemovePass((ushort)a.Id);
            RemovePass((ushort)b.Id);
            
            var tempId = a.Id;
            a.Id = b.Id;
            b.Id = tempId;

            AddPass(a);
            AddPass(b);


        }

    }
}
