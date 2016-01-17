package anon.psd.background.service;

/**
 * Created by Dmitry on 13.11.2015.
 */
public enum PasswordPolicy
{
    public static PasswordPolicy fromInteger(int x)
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
