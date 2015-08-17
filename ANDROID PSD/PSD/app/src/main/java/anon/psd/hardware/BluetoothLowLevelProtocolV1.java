package anon.psd.hardware;

import java.io.IOException;
import java.io.InputStream;

import anon.psd.utils.ArrayUtils;

/**
 * Created by Dmitry on 14.08.2015.
 */
public class BluetoothLowLevelProtocolV1 implements IBluetoothLowLevelProtocol
{
    private final static int MESSAGE_LENGTH = 32;
    private final static int TYPE_LENGTH = 1;

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
        return ArrayUtils.concatArrays(header, data);
    }

    @Override
    public byte[] prepareDisconnectMessage()
    {
        return new byte[0];
    }

    @Override
    public LowLevelMessage receiveMessage(InputStream stream)
    {
        byte[] typeBytes = new byte[TYPE_LENGTH];
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
                return new LowLevelMessage(LowLevelMsgType.Response, receiveMessageBytes(stream));
            default:
                return new LowLevelMessage(LowLevelMsgType.Unknown, typeBytes);
        }
    }

    private byte[] receiveMessageBytes(InputStream stream)
    {
        byte[] message = new byte[MESSAGE_LENGTH];
        try {
            stream.read(message);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }

        return message;
    }

}
