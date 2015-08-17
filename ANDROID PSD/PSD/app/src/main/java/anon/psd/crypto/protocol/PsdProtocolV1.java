package anon.psd.crypto.protocol;

import android.util.Log;

import java.io.IOException;
import java.security.InvalidKeyException;

import anon.psd.crypto.KeyGenerator;
import anon.psd.global.Constants;
import anon.psd.utils.ArrayUtils;

import static anon.psd.crypto.HashProvider.sha256Bytes;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdProtocolV1 implements IProtocol
{
    private byte[] btKey;
    private byte[] hBtKey;

    private byte[] nextBtKey;
    private byte[] nextHBtKey;

   public static String TAG = "protocol_debug";

    public PsdProtocolV1(byte[] currBtKey, byte[] currHBtKey)
    {
        btKey = currBtKey;
        hBtKey = currHBtKey;
    }


    @Override
    public byte[] generateNextMessage(short index, byte[] passPart1)
    {
        //generate new keys
        nextBtKey = KeyGenerator.generateRandomBtKey();
        nextHBtKey = KeyGenerator.generateRandomHBtKey();

        Log.i(TAG, String.format("HBTKey: %s", ArrayUtils.getHexArray(hBtKey)));
        Log.i(TAG, String.format("BTKey: %s", ArrayUtils.getHexArray(btKey)));
        Log.i(TAG, "-------------------");
        Log.i(TAG, String.format("Next HBTKey: %s", ArrayUtils.getHexArray(nextHBtKey)));
        Log.i(TAG, String.format("Next BTKey: %s", ArrayUtils.getHexArray(nextBtKey)));
        Log.i(TAG, "-----------------------------------------------------");

        //do crypto
        byte[] tempMessagePayload = generateTempMessagePayload(index, passPart1, nextBtKey, nextHBtKey);
        byte[] message = new byte[0];
        try {
            message = new ProtocolCrypto(btKey, hBtKey).generateSignedEncryptedMessage(tempMessagePayload);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;//return null and don't switch keys
        } catch (IOException e) {
            e.printStackTrace();
            return null;//return null and don't switch keys
        }
        return message;
    }


    private byte[] generateTempMessagePayload(short index, byte[] passPart1, byte[] nextBtKey, byte[] nextHBtKey)
    {
        byte[] indexBytes = getIndexBytes(index);
        return ArrayUtils.concatArrays(indexBytes, passPart1, nextBtKey, nextHBtKey);
    }

    private byte[] getIndexBytes(short index)
    {
        byte[] res = new byte[Constants.INDEX_BYTES];
        int offset = 0;
        res[offset++] = (byte) (index >> 8);
        res[offset] = (byte) index;
        return res;
    }


    @Override
    public boolean checkResponse(byte[] message)
    {
        byte[] expectedMessage = sha256Bytes(hBtKey);
        boolean responseCorrect = ArrayUtils.safeCompare(expectedMessage, message);

        if (responseCorrect)
            rollKeys();
        return responseCorrect;
    }


    public void rollKeys()
    {
        btKey = nextBtKey;
        hBtKey = nextHBtKey;

        Log.i(TAG, "\t\t\t\t\t\t [ Keys roll ]");

    }

    @Override
    public void setEncryptionData(byte[] newBtKey, byte[] newHBtKey)
    {
        btKey = newBtKey;
        hBtKey = newHBtKey;
    }
}
