package anon.psd.communication;

/**
 * Created by Dmitry on 28/02/2016.
 */
public enum PSDState {
    Disconnected,
    ReadyToSend,
    Awaiting;

    public static PSDState fromInteger(int x)
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
