package anon.psd.background.messages;

/**
 * Created by Dmitry on 31.07.2015.
 */
public enum RequestType
{
    ConnectService,
    Init,
    ConnectPSD,
    DisconnectPSD,
    SendPass,
    UpdateState,
    RollKeys,
    Kill;

    public static RequestType fromInteger(int x)
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
