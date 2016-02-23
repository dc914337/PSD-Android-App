package anon.psd.crypto;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class HashProvider
{

    public static String base64Sha256String(String input)
    {
        return Base64.encodeToString(sha256Bytes(input.getBytes()), Base64.DEFAULT);
    }

    public static byte[] PBKDF2(char[] input,byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Number of PBKDF2 hardening rounds to use. Larger values increase
        // computation time. You should select a value that causes computation
        // to take >100ms.
        final int iterations = 10000;

        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(input, salt, iterations, outputKeyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey.getEncoded();
    }



    public static byte[] sha256Bytes(byte[] input)
    {
        MessageDigest mda = null;
        try {
            mda = MessageDigest.getInstance("SHA-256", "BC");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mda.digest(input);
    }

    public static byte[] HMAC_SHA256(byte[] input, byte[] key)
    {
        Mac sha256_HMAC;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        SecretKeySpec secret_key = new SecretKeySpec(key, "HmacSHA256");
        try {
            sha256_HMAC.init(secret_key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return sha256_HMAC.doFinal(input);
    }
}
