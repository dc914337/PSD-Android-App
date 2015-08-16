package anon.psd.device;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 */
public enum ConnectionState
{
    Connected,
    Disconnected,
    LowSignal;

    public static ConnectionState fromInteger(int x)
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
