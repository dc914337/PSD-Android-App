package anon.psd.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import anon.psd.global.Constants;

import static anon.psd.utils.ArrayUtils.concatArrays;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class KeyGenerator
{
    static SecureRandom sr = new SecureRandom();

    public static byte[] getBaseKeyFromUserKey(String userKey)
    {
        byte[] passwordHash = new byte[0];
        try {
            char[] keyBytes=userKey.toCharArray();
            passwordHash = HashProvider.PBKDF2(keyBytes, Constants.BASE_PASS_SALT.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return passwordHash;
    }

    public static byte[] generateRandomBtKey()
    {
        return generateSecureBytes(Constants.BT_KEY_LENGTH);
    }

    public static byte[] generateRandomHBtKey()
    {
        return generateSecureBytes(Constants.HMAC_BT_KEY_LENGTH);
    }

    public static byte[] generateSResp()
    {
        return generateSecureBytes(Constants.SECRET_RESPONSE_LENGTH);
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
