package anon.psd.crypto;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import anon.psd.utils.ArrayUtils;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class BaseCrypto
{
    public static final String TAG = "BaseCrypto";

    public static final int ivLength = 16;
    private byte[] key;

    public BaseCrypto(byte[] key)
    {
        this.key = key;
    }

    public byte[] decryptAll(byte[] data)
    {
        byte[] IV = extractIV(data);
        byte[] encryptedData = extractEncryptedData(data);
        if (IV == null)
            return null;
        try {
            return decrypt(key, IV, encryptedData);
        } catch (Exception ex) {
            Log.e(TAG, "Something broke", ex);
            return null;
        }
    }

    public byte[] encryptAll(byte[] data)
    {
        byte[] IV = KeyGenerator.generateIV();

        byte[] encryptedData;
        try {
            encryptedData = encrypt(key, IV, data);
        } catch (Exception ex) {
            Log.e(TAG, "Something broke", ex);
            return null;
        }
        return ArrayUtils.concatArrays(IV, encryptedData);
    }

    private static byte[] extractEncryptedData(byte[] data)
    {
        int encryptedDataLength = data.length - ivLength;
        byte[] encryptedData = new byte[encryptedDataLength];
        System.arraycopy(data, ivLength, encryptedData, 0, encryptedDataLength);
        return encryptedData;
    }


    private static byte[] extractIV(byte[] data)
    {
        if (data.length < ivLength)
            return null;
        byte[] IV = new byte[ivLength];
        System.arraycopy(data, 0, IV, 0, ivLength);
        return IV;
    }

    private static byte[] encrypt(byte[] key, byte[] IV, byte[] data) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException
    {
        SecretKey aesKey = new SecretKeySpec(key, 0, key.length, "AES");
        // Encrypt cipher
        Cipher encryptCipher = null;
        try {
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(IV));

        ByteArrayInputStream inStream = new ByteArrayInputStream(data);
        CipherInputStream cipherInputStream = new CipherInputStream(inStream, encryptCipher);

        byte[] buf = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((bytesRead = cipherInputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, bytesRead);
        }

        byte[] res = outputStream.toByteArray();

        cipherInputStream.close();
        inStream.close();
        outputStream.close();
        return res;
    }

    private static byte[] decrypt(byte[] key, byte[] IV, byte[] encryptedBytes) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException
    {
        SecretKey aesKey = new SecretKeySpec(key, 0, key.length, "AES");

        // Decrypt cipher
        Cipher decryptCipher = null;
        try {
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(IV));

        // Decrypt

        ByteArrayInputStream inStream = new ByteArrayInputStream(encryptedBytes);
        CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);

        byte[] buf = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((bytesRead = cipherInputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, bytesRead);
        }

        byte[] res = outputStream.toByteArray();

        cipherInputStream.close();
        inStream.close();
        outputStream.close();
        return res;
    }


}
