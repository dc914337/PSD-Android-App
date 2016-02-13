package anon.psd.hardware.bluetooth.lowlevelV1;

/**
 * Created by Dmitry on 16.08.2015.
 */
public enum LowLevelMsgType
{
    Pong(0xFF),
    Response(0xEE),
    Unknown(0x00);


    byte type;

    LowLevelMsgType(int type)
    {
        this.type = (byte) type;
    }

    public static LowLevelMsgType fromByte(byte typeByte)
    {
        for (LowLevelMsgType t : values())
            if (t.type == typeByte)
                return t;
        return Unknown;
    }
}
