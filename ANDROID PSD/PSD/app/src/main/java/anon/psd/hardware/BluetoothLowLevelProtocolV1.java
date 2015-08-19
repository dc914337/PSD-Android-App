package anon.psd.hardware;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import anon.psd.utils.ArrayUtils;

/**
 * Created by Dmitry on 14.08.2015.
 */
public class BluetoothLowLevelProtocolV1 implements IBluetoothLowLevelProtocol
{
    private final static int MESSAGE_LENGTH = 32;
    private final static int PONG_LENGTH = 1;
    private final static int TYPE_LENGTH = 1;
    private final static String TAG = "LowLevel";
    private final static int nextByteTimeoutMs = 200;
    private final static int SLEEP_BETWEEN_TRIES = 10;

    @Override
    public byte[] prepareConnectionMessage()
    {
        byte[] alive = new byte[]{(byte) 0xFF};
        Log.d(TAG, "[ SEND ] Connect");
        return alive;
    }

    @Override
    public byte[] prepareSendMessage(byte[] data)
    {
        byte[] header = new byte[]{(byte) 0xDE, (byte) 0xAD};
        Log.d(TAG, "[ SEND ] Length: " + (data.length + 2));
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
        Log.d(TAG, "[ RECEIVED ] [ TYPE ] " + type.toString());
        switch (type) {
            case Pong:
                return checkPong(stream);
            case Response:
                return new LowLevelMessage(LowLevelMsgType.Response, receiveNextBytes(MESSAGE_LENGTH, stream));
            default:
                return new LowLevelMessage(LowLevelMsgType.Unknown, typeBytes);
        }
    }

    private LowLevelMessage checkPong(InputStream stream)
    {
        byte[] receivedBytes = receiveNextBytes(PONG_LENGTH, stream);
        if (receivedBytes == null ||
                LowLevelMsgType.fromByte(receivedBytes[0]) != LowLevelMsgType.Pong)
            return new LowLevelMessage(LowLevelMsgType.Unknown, receivedBytes);
        Log.d(TAG, "[ RECEIVED ] [ MESSAGE ] PONG CONFIRM");
        return new LowLevelMessage(LowLevelMsgType.Pong, receivedBytes);
    }


    private byte[] receiveNextBytes(int count, InputStream stream)
    {
        try {
            int read = 0;
            int failedTries = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (read < count && failedTries * SLEEP_BETWEEN_TRIES < nextByteTimeoutMs) {
                int available = stream.available();
                if (available <= 0) {
                    Thread.sleep(SLEEP_BETWEEN_TRIES);
                    failedTries++;
                    continue;
                }
                failedTries = 0;
                byte[] buff = new byte[available];
                read += stream.read(buff);
                baos.write(buff);
            }
            Log.d(TAG, "[ RECEIVED ] [ MESSAGE ] Length: " + baos.size());
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
