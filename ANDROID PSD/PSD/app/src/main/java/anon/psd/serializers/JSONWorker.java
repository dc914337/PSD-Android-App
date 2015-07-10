package anon.psd.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import anon.psd.models.DataBase;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class JSONWorker
{

    public static String serialize(DataBase passwordsBase)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(passwordsBase);
    }

    public static DataBase deserialize(String json)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        DataBase base = gson.fromJson(json, DataBase.class);
        return base;
    }

}
