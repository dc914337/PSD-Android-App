package anon.psd.background;

/**
 * Created by Dmitry on 17.08.2015.
 */
public enum ErrorType
{
    DeviceNotConnected,
    DBEror,
    IOError,
    WrongState;


    public static ErrorType fromInteger(int x)
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
