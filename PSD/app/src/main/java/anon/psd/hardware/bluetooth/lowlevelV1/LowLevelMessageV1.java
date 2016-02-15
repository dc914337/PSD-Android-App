package anon.psd.hardware.bluetooth.lowlevelV1;

/**
 * Created by Dmitry on 16.08.2015.
 */
public class LowLevelMessageV1
{
    public LowLevelMsgType type;
    public byte[] message;

    public LowLevelMessageV1(LowLevelMsgType type, byte[] message)
    {
        this.type = type;
        this.message = message;
    }

}
