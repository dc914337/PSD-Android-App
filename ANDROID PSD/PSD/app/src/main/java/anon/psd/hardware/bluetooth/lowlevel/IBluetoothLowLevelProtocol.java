package anon.psd.hardware.bluetooth.lowlevel;

import java.io.InputStream;

/**
 * Created by Dmitry on 14.08.2015.
 */
public interface IBluetoothLowLevelProtocol
{
    public byte[] preparePingMessage();

    public byte[] prepareSendMessage(byte[] data);

    public byte[] prepareDisconnectMessage();

    public LowLevelMessage receiveMessage(InputStream stream);
}
