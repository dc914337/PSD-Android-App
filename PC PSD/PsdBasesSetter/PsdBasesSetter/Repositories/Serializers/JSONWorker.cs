using Newtonsoft.Json;
using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter.Repositories.Serializers
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
