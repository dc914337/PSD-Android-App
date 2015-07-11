package anon.psd.crypto;

import java.util.Arrays;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class KeyGenerator
{
    public static final String BASE_PASS_SALT = "phone_base_salt-v1";

    public static byte[] getBasekeyFromUserkey(String userKey)
    {

        byte[] passwordHash = HashProvider.sha256Bytes(userKey.getBytes());
        byte[] saltBytes = BASE_PASS_SALT.getBytes();
        byte[] saltedHash = HashProvider.sha256Bytes(concatArrays(passwordHash, saltBytes));
        return saltedHash;
    }


    private static byte[] concatArrays(byte[] first, byte[] second)
    {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
