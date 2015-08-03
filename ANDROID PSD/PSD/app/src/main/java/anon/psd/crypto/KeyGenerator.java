package anon.psd.crypto;

import java.security.SecureRandom;
import java.util.Arrays;

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class KeyGenerator
{
    static SecureRandom sr = new SecureRandom();

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

    public static byte[] generateRandomBtKey()
    {
        return generateSecureBytes(Constants.BT_KEY_LENGTH);
    }

    public static byte[] generateRandomHBtKey()
    {
        return generateSecureBytes(Constants.HMAC_BT_KEY_LENGTH);
    }

    private static byte[] generateSecureBytes(int length)
    {
        byte[] newBytes = new byte[length];
        sr.nextBytes(newBytes);
        return newBytes;
    }

    public static byte[] generateIV()
    {
        return generateSecureBytes(Constants.IV_BYTES);
    }
}
