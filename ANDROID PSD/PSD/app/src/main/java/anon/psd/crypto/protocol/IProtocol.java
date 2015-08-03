package anon.psd.crypto.protocol;

/**
 * Created by Dmitry on 03.08.2015.
 */
public interface IProtocol
{
    public byte[] generateNextMessage(int index, byte[] passPart1);

    public boolean checkResponse(byte[] message);

    public void setEncryptionData(byte[] btKey,byte[] hBtKey);

}
