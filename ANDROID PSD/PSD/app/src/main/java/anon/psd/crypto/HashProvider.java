package anon.psd.crypto;

import android.util.Log;

import java.security.MessageDigest;

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class HashProvider
{
    public static String sha256String(String input)
    {
        return new String(sha256Bytes(input.getBytes()));
    }


    public static byte[] sha256Bytes(byte[] input)
    {
        MessageDigest mda = null;
        try {
            mda = MessageDigest.getInstance("SHA-256", "BC");
        } catch (Exception e) {
            Log.wtf(Constants.LTAG, e);
            e.printStackTrace();
            return null;
        }
        return mda.digest(input);
    }
}
