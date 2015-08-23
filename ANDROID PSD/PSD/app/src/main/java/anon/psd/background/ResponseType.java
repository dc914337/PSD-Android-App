package anon.psd.background;

/**
 * Created by Dmitry on 23.08.2015.
 */
public enum ResponseType
{
    PassSentSuccess,
    StateChanged,
    Error;

    public static ResponseType fromInteger(int x)
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
