package anon.psd.serializers;

import anon.psd.models.DataBase;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class Serializer
{
    public static String Serialize(DataBase passwordsList)
    {
        return JSONWorker.serialize(passwordsList);
    }

    public static DataBase Deserialize(String input)
    {
        return JSONWorker.deserialize(input);
    }
}
