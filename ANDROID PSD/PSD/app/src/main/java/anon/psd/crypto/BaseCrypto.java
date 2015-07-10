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

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class BaseCrypto
{
    public static final int IVLength = 16;
    private byte[] key;

    public BaseCrypto(byte[] key)
    {
        this.key = key;
    }

    public byte[] decryptAll(byte[] data)
    {
        byte[] IV = extractIV(data);

        if (IV == null)
            return null;
        try {
            return decrypt(key, IV, data);
        } catch (Exception ex) {
            Log.e(Constants.LTAG, "Something broke", ex);
            return null;
        }
    }


    private static byte[] extractIV(byte[] data)
    {
        if (data.length < IVLength)
            return null;
        byte[] IV = new byte[IVLength];
        System.arraycopy(data, 0, IV, 0, IVLength);
        return IV;
    }


    private static byte[] decrypt(byte[] key, byte[] IV, byte[] encryptedBytes) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException
    {
        SecretKey aesKey = new SecretKeySpec(key, 0, key.length, "AES");

        // Decrypt cipher
        Cipher decryptCipher = null;
        try {
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(IV));

        // Decrypt
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = new ByteArrayInputStream(encryptedBytes);
        CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = cipherInputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, bytesRead);
        }

        byte[] res = outputStream.toByteArray();
        outputStream.close();
        inStream.close();
        cipherInputStream.close();
        return res;
    }


}
