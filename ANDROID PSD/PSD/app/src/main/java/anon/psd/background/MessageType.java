package anon.psd.background;

/**
 * Created by Dmitry on 31.07.2015.
 */
public enum MessageType
{
    ConnectService,
    Init,
    Connect,
    Disconnect,
    Password,
    Result,
    ConnectionState;

    public static MessageType fromInteger(int x)
    {
        return values()[x];
    }

    public int getInt()
    {
        for (int i = 0; i < values().length; i++) {
            if (this.equals(values()[i]))
                return i;
        }
        throw new IndexOutOfBoundsException("Wrong enum");
    }
}
