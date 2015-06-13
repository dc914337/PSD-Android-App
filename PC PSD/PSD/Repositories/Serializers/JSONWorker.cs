using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Json;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using PSD.Repositories.Objects;

namespace PSD.Repositories.Serializers
{
    class JSONWorker
    {
        public static string Serialize(Base passwordsBase)
        {
            return JsonConvert.SerializeObject(passwordsBase);
        }

        public static Base Deserialize(string json)
        {
            return JsonConvert.DeserializeObject<Base>(json);
        }
    }
}
