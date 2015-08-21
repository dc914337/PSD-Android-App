package anon.psd.hardware.bluetooth.lowlevel;

/**
 * Created by Dmitry on 16.08.2015.
 */
public class LowLevelMessage
{
    public LowLevelMsgType type;
    public byte[] message;

    public LowLevelMessage(LowLevelMsgType type, byte[] message)
    {
        this.type = type;
        this.message = message;
    }

}
