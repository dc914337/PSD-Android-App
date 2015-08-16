package anon.psd.hardware;

import java.io.IOException;
import java.io.InputStream;

import anon.psd.utils.ArraysUtils;

/**
 * Created by Dmitry on 14.08.2015.
 */
public class BluetoothLowLevelProtocolV1 implements IBluetoothLowLevelProtocol
{
    @Override
    public byte[] prepareConnectionMessage()
    {
        byte[] alive = new byte[]{(byte) 0xFF};
        return alive;
    }

    @Override
    public byte[] prepareSendMessage(byte[] data)
    {
        byte[] header = new byte[]{(byte) 0xDE, (byte) 0xAD};
        return ArraysUtils.concatArrays(header, data);
    }

    @Override
    public byte[] prepareDisconnectMessage()
    {
        return new byte[0];
    }

    @Override
    public LowLevelMessage receiveMessage(InputStream stream)
    {
        byte[] typeBytes = new byte[1];
        try {
            stream.read(typeBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LowLevelMsgType type = LowLevelMsgType.fromByte(typeBytes[0]);
        switch (type) {
            case Pong:
                return new LowLevelMessage(LowLevelMsgType.Pong, null);
            case Response:
                return new LowLevelMessage(LowLevelMsgType.Response, receiveResponse(stream));
            default:
                return null;
        }
    }


    private byte[] receiveResponse(InputStream stream)
    {
        byte[] buffer = new byte[32];
        try {
            stream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return buffer;
    }

}
