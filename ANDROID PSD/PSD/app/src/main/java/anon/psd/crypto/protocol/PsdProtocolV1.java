package anon.psd.crypto.protocol;

import anon.psd.crypto.KeyGenerator;
import anon.psd.global.Constants;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdProtocolV1 implements IProtocol
{
    private byte[] btKey;
    private byte[] hBtKey;

    private byte[] prevBtKey;
    private byte[] prevHBtKey;


    public PsdProtocolV1(byte[] currBtKey, byte[] currHBtKey)
    {
        btKey = currBtKey;
        hBtKey = currHBtKey;
    }


    @Override
    public byte[] generateNextMessage(int index, byte[] passPart1)
    {
        //generate new keys
        byte[] nextBtKey = KeyGenerator.generateRandomBtKey();
        byte[] nextHBtKey = KeyGenerator.generateRandomHBtKey();

        //do crypto
        byte[] tempMessagePayload = generateTempMessagePayload(index, passPart1, nextBtKey, nextHBtKey);
        byte[] message = new ProtocolCrypto(btKey, hBtKey).generateSignedEncryptedMessage(tempMessagePayload);

        //remember keys
        prevBtKey = btKey;
        prevHBtKey = hBtKey;

        return message;
    }

    private byte[] generateTempMessagePayload(int index, byte[] passPart1, byte[] nextBtKey, byte[] nextHBtKey)
    {
        byte[] indexBytes = getIndexBytes(index);
        byte[] tempMessage = concatArrays(new byte[][]{indexBytes, passPart1, nextBtKey, nextHBtKey});
        return tempMessage;
    }

    private byte[] concatArrays(byte[][] arrays)
    {
        int totalLength = 0;
        for (byte[] arr : arrays) {
            totalLength += arr.length;
        }

        byte[] resArr = new byte[totalLength];

        int offset = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, resArr, offset, arr.length);
            offset += arr.length;
        }
        return resArr;
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
        return false;
    }

    @Override
    public void setEncryptionData(byte[] newBtKey, byte[] newHBtKey)
    {
        btKey = newBtKey;
        hBtKey = newHBtKey;
    }
}
