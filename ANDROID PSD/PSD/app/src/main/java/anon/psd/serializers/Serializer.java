package anon.psd.serializers;

import java.util.ArrayList;

import anon.psd.models.AppearancesList;
import anon.psd.models.DataBase;
import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 10.07.2015.
 */

/*
    This class can help easily change serialization format for each datatype
 */
public class Serializer
{
    public static String serializeDataBase(DataBase base)
    {
        return JSONWorker.serializeDataBase(base);
    }

    public static DataBase deserializeDataBase(String input)
    {
        return JSONWorker.deserializeDataBase(input);
    }

    public static String serializePasswordAppearances(ArrayList<PrettyPassword> passes)
    {
        return JSONWorker.serializePasswordAppearances(passes);
    }

    public static AppearancesList deserializePasswordAppearances(String input)
    {
        return JSONWorker.deserializePasswordAppearances(input);
    }
}
