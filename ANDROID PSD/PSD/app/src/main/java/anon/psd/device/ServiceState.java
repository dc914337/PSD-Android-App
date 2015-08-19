package anon.psd.device;

import android.util.Log;

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

    public boolean isPsdConnected()
    {
        switch (this) {
            case NotInitialised:
            case NotConnected:
                return false;
            case ReadyToSend:
            case LowSignal:
            case WaitingResponse:
                return true;
            default:
                Log.wtf("WTF", "ServiceState unknown state");
                return false;
        }
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
