package anon.psd.crypto.protocol.packages;

/**
 * Created by dmitry on 2/13/16.
 */
public enum PackageTypeV2 {
    Ping,
    RawKeys,
    SendPass,
    AddPass,
    RemovePass;

    public static PackageTypeV2 fromInteger(int x)
    {
        return values()[x];
    }

    public byte getByte()
    {
        for (byte i = 0; i < values().length; i++) {
            if (this.equals(values()[i]))
                return i;
        }
        throw new IndexOutOfBoundsException("Wrong enum");
    }

}
