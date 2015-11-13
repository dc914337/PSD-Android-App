package anon.psd.gui.exchange;

import java.util.Hashtable;

/**
 * Created by ANONYMOUS on 24.07.2015.
 */

/*
This class is a crutch to transfer ref to object from activity to activity.
Use ONLY if activities are in one process!
 */
public class ActivitiesExchange
{
    private static Hashtable<String, Object> innerDict = new Hashtable<>();

    public static void addObject(String key, Object value)
    {
        innerDict.put(key, value);
    }

    public static <T> T getObject(String key)
    {
        return (T) innerDict.get(key);
    }


    public static <T> T getAndRemoveObject(String key)
    {
        T ret = (T) innerDict.get(key);
        innerDict.remove(key);
        return ret;
    }

}
