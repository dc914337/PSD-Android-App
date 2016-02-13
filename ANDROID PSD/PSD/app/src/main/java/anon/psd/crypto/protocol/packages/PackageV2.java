package anon.psd.crypto.protocol.packages;

import anon.psd.utils.ShortUtils;

/**
 * Created by dmitry on 2/13/16.
 */
public abstract class PackageV2 {
   public PackageTypeV2 type;
    public byte[] getBytes()
    {
        return new byte[]{type.getByte()};
    }
}
