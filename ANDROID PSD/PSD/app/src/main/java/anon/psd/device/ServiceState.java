package anon.psd.device;

/**
 * Created by Dmitry on 17.08.2015.
 */
public enum ServiceState
{
    NotInitialised,
    NotConnected,
    ReadyToSend,
    LowSignal,
    WaitingResponse;

    public static ServiceState fromInteger(int x)
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
