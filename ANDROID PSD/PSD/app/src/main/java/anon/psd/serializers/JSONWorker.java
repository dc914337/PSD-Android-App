package anon.psd.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import anon.psd.models.AppearancesList;
import anon.psd.models.DataBase;
import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class JSONWorker
{

    public static String serializeDataBase(DataBase passwordsBase)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(passwordsBase);
    }

    public static DataBase deserializeDataBase(String json)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        DataBase base = gson.fromJson(json, DataBase.class);
        return base;
    }


    public static String serializePasswordAppearances(ArrayList<PrettyPassword> passwordAppearance)
    {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(passwordAppearance);
    }

    public static AppearancesList deserializePasswordAppearances(String json)
    {
        Gson gson = new GsonBuilder().create();

        AppearancesList passes = gson.fromJson(json, AppearancesList.class);
        return passes;
    }

}
