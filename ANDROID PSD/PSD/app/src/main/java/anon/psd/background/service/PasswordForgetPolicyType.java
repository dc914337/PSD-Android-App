package anon.psd.background.service;

/**
 * Created by Dmitry on 13.11.2015.
 */
public enum PasswordForgetPolicyType
{
    OnAppClose,
    After30Minutes,
    WhileServiceAlive,
    SavePassInPrefs;


    public static PasswordForgetPolicyType fromInteger(int x)
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
