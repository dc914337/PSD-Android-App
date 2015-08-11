package anon.psd.background;

/**
 * Created by Dmitry on 31.07.2015.
 */
public enum MessageTypes
{
    ConnectService,
    Connect,
    Disconnect,
    Send,
    Receive,
    ConnectionState;

    public static MessageTypes fromInteger(int x)
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
