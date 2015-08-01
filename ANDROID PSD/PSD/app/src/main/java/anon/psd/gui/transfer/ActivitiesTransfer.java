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
    private static Hashtable<String, Object> innerDict = new Hashtable<>();

    public static void sendTransferringObject(String key, Object value)
    {
        innerDict.put(key, value);
    }

    public static Object receiveTransferringObject(String key)
    {
        Object receivedObject = innerDict.get(key);
        //remove from dict
        //innerDict.remove(key); //activity will crash if couldn't find this value
        return receivedObject;
    }

}
