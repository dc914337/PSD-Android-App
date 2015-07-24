package anon.psd.gui.transfer;

import java.util.Hashtable;

/**
 * Created by ANONYMOUS on 24.07.2015.
 */

/*
This class is a crutch to transfer ref to object from activity to activity.
Use ONLY if activities are in one process!
 */
public class ActivitiesTransfer
{
    private static Hashtable<String, Object> _innerDict = new Hashtable<>();

    public static void sendTransferringObject(String key, Object value)
    {
        _innerDict.put(key, value);
    }

    public static Object recieveTransferringObject(String key)
    {
        Object recievedObject = _innerDict.get(key);
        //remove from dict
        _innerDict.remove(key);
        return recievedObject;
    }

}
