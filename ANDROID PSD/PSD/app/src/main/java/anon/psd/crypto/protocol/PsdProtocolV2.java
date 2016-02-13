package anon.psd.crypto.protocol;

import java.io.IOException;
import java.security.InvalidKeyException;

import anon.psd.crypto.KeyGenerator;
import anon.psd.crypto.protocol.packages.PackageV2;
import anon.psd.utils.ArrayUtils;
import anon.psd.utils.ShortUtils;

import static anon.psd.crypto.HashProvider.sha256Bytes;
import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by dmitry on 2/13/16.
 */
public class PsdProtocolV2 {

    public byte[] btKey;
    public byte[] hBtKey;

    private byte[] nextBtKey;
    private byte[] SResp; //secret response

    public PsdProtocolV2(byte[] startBtKey, byte[] HBtKey)
    {
        btKey = startBtKey;
        hBtKey = HBtKey;
    }

    public byte[] generateNextSignedEncryptedPackage(PackageV2 packagev2)
    {
        //generate new keys
        nextBtKey = KeyGenerator.generateRandomBtKey();
        SResp=KeyGenerator.generateSResp();
        Log(this, "[ PROTOCOL V2 ] Generated "+packagev2.type );
        return generateSignedEncryptedMessage(packagev2);
    }

    private byte[] generateSignedEncryptedMessage(PackageV2 packagev2)
    {
        byte[] payload= ArrayUtils.concatArrays(nextBtKey,SResp,packagev2.getBytes());
        byte[] message = new byte[0];

        try {
            message = new ProtocolCrypto(btKey, hBtKey).generateSignedEncryptedMessage(payload);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;//return null and don't switch keys
        } catch (IOException e) {
            e.printStackTrace();
            return null;//return null and don't switch keys
        }

        byte[] messageWithSize = ArrayUtils.concatArrays(
                ShortUtils.getShortBytes((byte)message.length),
                message
        );
        return messageWithSize;
    }


    public boolean checkResponse(byte[] message)
    {
        byte[] expectedMessage = sha256Bytes(SResp);
        boolean responseCorrect = ArrayUtils.safeCompare(expectedMessage, message);

        if (responseCorrect)
            rollKeys();
        return responseCorrect;
    }

    public void rollKeys()
    {
        btKey = nextBtKey;
        Log(this, "[ PROTOCOL ] \t\t\t\t\t\t\t[ Keys roll ]");
    }
}
