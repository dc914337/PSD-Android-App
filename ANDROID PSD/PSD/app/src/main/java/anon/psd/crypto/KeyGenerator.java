package anon.psd.crypto;

import java.util.Arrays;

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class KeyGenerator
{


    public static byte[] getBasekeyFromUserkey(String userKey)
    {
        byte[] passwordHash = HashProvider.sha256Bytes(userKey.getBytes());
        byte[] saltBytes = Constants.BASE_PASS_SALT.getBytes();
        return HashProvider.sha256Bytes(concatArrays(passwordHash, saltBytes));
    }


    private static byte[] concatArrays(byte[] first, byte[] second)
    {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
