package anon.psd.device.state;

public enum ServiceState
{
    NotInitialised,
    Initialised;

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


