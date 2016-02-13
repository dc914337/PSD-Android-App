package anon.psd.crypto.protocol.packages.V2;

import anon.psd.crypto.protocol.packages.PackageTypeV2;
import anon.psd.crypto.protocol.packages.PackageV2;
import anon.psd.models.Password;
import anon.psd.utils.ArrayUtils;
import anon.psd.utils.ShortUtils;

/**
 * Created by dmitry on 2/13/16.
 */
public class AddPassPackage extends PackageV2
{
    private byte[] partBytes;
    byte[] passIdBytes;

    public AddPassPackage(short passId,Password passPart2)
    {
        this.partBytes=passPart2.bytes;
        this.passIdBytes= ShortUtils.getShortBytes(passId);
        type= PackageTypeV2.AddPass;
    }

    @Override
    public byte[] getBytes() {
        return ArrayUtils.concatArrays(super.getBytes(), passIdBytes, partBytes);
    }
}