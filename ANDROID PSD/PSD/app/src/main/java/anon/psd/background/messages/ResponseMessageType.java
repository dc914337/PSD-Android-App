package anon.psd.background.messages;

/**
 * Created by Dmitry on 01.11.2015.
 */
public enum ResponseMessageType
{

    PassSentSuccess;

    public static ResponseMessageType fromInteger(int x)
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
