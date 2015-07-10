package anon.psd.serializers;

import anon.psd.models.DataBase;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class JSONWorker
{

    public static String Serialize(DataBase passwordsBase)
    {
        return "";// JsonConvert.SerializeObject(passwordsBase);
    }

    public static DataBase Deserialize(String json)
    {
        return new DataBase();//JsonConvert.DeserializeObject<DataBase>(json);
    }

}
