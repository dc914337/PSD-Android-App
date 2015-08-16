package anon.psd.hardware;

import java.io.InputStream;

/**
 * Created by Dmitry on 14.08.2015.
 */
public interface IBluetoothLowLevelProtocol
{
    public byte[] prepareConnectionMessage();

    public byte[] prepareSendMessage(byte[] data);

    public byte[] prepareDisconnectMessage();

    public LowLevelMessage receiveMessage(InputStream stream);
}
