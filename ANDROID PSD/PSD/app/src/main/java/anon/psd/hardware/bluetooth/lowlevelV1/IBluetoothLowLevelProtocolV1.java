package anon.psd.hardware.bluetooth.lowlevelV1;

import java.io.InputStream;

/**
 * Created by Dmitry on 14.08.2015.
 */
public interface IBluetoothLowLevelProtocolV1
{
    public byte[] preparePingMessage();

    public byte[] prepareSendMessage(byte[] data);

    public byte[] prepareDisconnectMessage();

    public LowLevelMessageV1 receiveMessage(InputStream stream);
}
