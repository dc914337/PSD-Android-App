package anon.psd.hardware;

import java.io.InputStream;

import anon.psd.utils.ArraysUtils;

/**
 * Created by Dmitry on 14.08.2015.
 */
public class BluetoothLowLevelProtocolStub implements IBluetoothLowLevelProtocol
{
    @Override
    public byte[] prepareConnectionMessage()
    {
        return "HI".getBytes();
    }

    @Override
    public byte[] prepareSendMessage(byte[] data)
    {
        byte[] header = "OK, GOOGLE! ".getBytes();
        return ArraysUtils.concatArrays(header, data);
    }

    @Override
    public byte[] prepareDisconnectMessage()
    {
        return "Elvis has left the building!".getBytes();
    }

    @Override
    public byte[] receiveMessage(InputStream stream)
    {
        return "new_received_message".getBytes();
    }
}
