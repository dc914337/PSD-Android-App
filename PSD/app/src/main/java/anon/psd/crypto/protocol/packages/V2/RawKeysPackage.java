package anon.psd.crypto.protocol.packages.V2;

import anon.psd.crypto.protocol.packages.PackageTypeV2;
import anon.psd.crypto.protocol.packages.PackageV2;
import anon.psd.utils.ArrayUtils;

/**
 * Created by dmitry on 2/13/16.
 */
public class RawKeysPackage extends PackageV2
{
    private byte[] keyCodes;


    public RawKeysPackage(byte[] keyCodes)
    {
        this.keyCodes=keyCodes;
        type= PackageTypeV2.RawKeys;
    }

    @Override
    public byte[] getBytes() {
        return ArrayUtils.concatArrays(super.getBytes(), new byte[]{(byte)keyCodes.length}, keyCodes);
    }
}
