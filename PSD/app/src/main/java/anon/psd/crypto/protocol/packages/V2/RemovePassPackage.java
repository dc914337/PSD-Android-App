package anon.psd.crypto.protocol.packages.V2;

import anon.psd.crypto.protocol.packages.PackageTypeV2;
import anon.psd.crypto.protocol.packages.PackageV2;
import anon.psd.models.Password;
import anon.psd.utils.ArrayUtils;
import anon.psd.utils.ShortUtils;

/**
 * Created by dmitry on 2/13/16.
 */
public class RemovePassPackage extends PackageV2
{
    byte[] passIdBytes;


    public RemovePassPackage(short passId)
    {
        this.passIdBytes= ShortUtils.getShortBytes(passId);
        type= PackageTypeV2.RemovePass;
    }

    @Override
    public byte[] getBytes() {

        return ArrayUtils.concatArrays(super.getBytes(), passIdBytes, passIdBytes);
    }
}