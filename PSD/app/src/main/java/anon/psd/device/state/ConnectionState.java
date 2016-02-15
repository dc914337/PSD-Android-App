package anon.psd.device.state;

/**
 * Created by Dmitry on 19.08.2015.
 */
public enum ConnectionState
{
    NotAvailable,
    Disconnected,
    Connected;

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
