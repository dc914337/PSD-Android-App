using System;

namespace PSDPrototype
{
    class ForeignPC
    {
        public Key enteredKey { get; set; }

        public ForeignPC()
        {
            enteredKey = new Key();
        }

        public void EnterPassword(String password)
        {
            enteredKey.KeyStr = password;
        }
    }
}