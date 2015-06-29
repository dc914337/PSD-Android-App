using PsdBasesSetter.Repositories.Objects;

namespace PsdBasesSetter.Repositories.Serializers
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