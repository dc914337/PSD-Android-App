package anon.psd.crypto;

import android.util.Log;

import java.security.MessageDigest;
import java.util.Arrays;

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class KeyConverter
{
    public static byte[] getBasekeyFromUserkey(String userKey)
    {
        MessageDigest mda = null;
        try {
            mda = MessageDigest.getInstance("SHA-512", "BC");
        } catch (Exception e) {
            Log.wtf(Constants.LTAG, e);
            e.printStackTrace();
        }
        byte[] passwordHash = mda.digest(userKey.getBytes());
        byte[] saltBytes = Constants.BASE_PASS_SALT.getBytes();
        byte[] saltedHash = mda.digest(concatArrays(passwordHash, saltBytes));
        return saltedHash;
    }


    public static byte[] concatArrays(byte[] first, byte[] second)
    {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
