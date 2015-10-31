package anon.psd.hardware.bluetooth.lowlevel;

/**
 * Created by Dmitry on 11.09.2015.
 */
public enum BTError
{
    SendingError,
    ConnectError,
    NoResponse,
    PongTimedOut;

    public static BTError fromInteger(int x)
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
