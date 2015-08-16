package anon.psd.crypto.protocol;

import java.io.IOException;
import java.security.InvalidKeyException;

import anon.psd.crypto.KeyGenerator;
import anon.psd.global.Constants;
import anon.psd.utils.ArraysUtils;

import static anon.psd.crypto.HashProvider.sha256Bytes;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdProtocolV1 implements IProtocol
{
    private byte[] btKey;
    private byte[] hBtKey;

    private byte[] prevBtKey;
    private byte[] prevHBtKey;

    private byte[] nextBtKey;
    private byte[] nextHBtKey;

    public PsdProtocolV1(byte[] currBtKey, byte[] currHBtKey)
    {
        btKey = currBtKey;
        hBtKey = currHBtKey;
    }


    @Override
    public byte[] generateNextMessage(int index, byte[] passPart1)
    {
        //generate new keys
        KeyGenerator.generateRandomBtKey();
        KeyGenerator.generateRandomHBtKey();

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


    private byte[] generateTempMessagePayload(int index, byte[] passPart1, byte[] nextBtKey, byte[] nextHBtKey)
    {
        byte[] indexBytes = getIndexBytes(index);
        return ArraysUtils.concatArrays(indexBytes, passPart1, nextBtKey, nextHBtKey);
    }

    private byte[] getIndexBytes(int index)
    {
        byte[] res = new byte[Constants.INDEX_BYTES];
        int offset = 0;
        res[offset++] = (byte) index;
        res[offset++] = (byte) (index >> 8);
        res[offset++] = (byte) (index >> 16);
        res[offset] = (byte) (index >> 24);
        return res;
    }


    @Override
    public boolean checkResponse(byte[] message)
    {
        byte[] expectedMessage = sha256Bytes(hBtKey);
        boolean responseCorrect = ArraysUtils.safeCompare(expectedMessage, message);

        if (responseCorrect)
            rollKeys();
        return responseCorrect;
    }


    public void rollKeys()
    {
        //remember keys to check response from psd later or recover if error
        //also switch current keys on new ones
        prevBtKey = btKey;
        prevHBtKey = hBtKey;

        btKey = nextBtKey;
        hBtKey = nextHBtKey;
    }

    @Override
    public void setEncryptionData(byte[] newBtKey, byte[] newHBtKey)
    {
        btKey = newBtKey;
        hBtKey = newHBtKey;
    }
}
