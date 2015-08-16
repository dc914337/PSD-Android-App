package anon.psd.crypto.protocol;

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

import anon.psd.crypto.KeyGenerator;

import static anon.psd.crypto.HashProvider.HMAC_SHA256;
import static anon.psd.utils.ArraysUtils.concatArrays;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class ProtocolCrypto
{
    byte[] btKey;
    byte[] hBtKey;
    byte[] iv;

    public ProtocolCrypto(byte[] btKey, byte[] hBtKey)
    {
        this.btKey = btKey;
        this.hBtKey = hBtKey;
        this.iv = KeyGenerator.generateIV();
    }


    public byte[] generateSignedEncryptedMessage(byte[] payload) throws InvalidKeyException, IOException
    {
        //encrypt tempMessage
        byte[] encryptedTempMessage = encryptTempMessage(payload);
        //count HMAC
        byte[] hmac = countTempMessageHMAC(encryptedTempMessage);
        //concat
        return concatArrays(iv, encryptedTempMessage, hmac);
    }


    private byte[] encryptTempMessage(byte[] tempMessage) throws InvalidKeyException, IOException
    {
        SecretKey aesKey = new SecretKeySpec(btKey, 0, btKey.length, "AES");
        // Encrypt cipher
        Cipher encryptCipher = null;
        try {
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        //Encrypt
        ByteArrayInputStream inStream = new ByteArrayInputStream(tempMessage);
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

    private byte[] countTempMessageHMAC(byte[] message)
    {
        return HMAC_SHA256(message, hBtKey);
    }
}
