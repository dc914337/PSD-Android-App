using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PSD.Repositories.Objects;

namespace PSD.Repositories.Serializers
{
    internal class Serializer
    {
        public static string Serialize(Base passwordsList)
        {
            return JSONWorker.Serialize(passwordsList);
        }

        public static Base Deserialize(string input)
        {
            return JSONWorker.Deserialize(input);
        }
    }
}