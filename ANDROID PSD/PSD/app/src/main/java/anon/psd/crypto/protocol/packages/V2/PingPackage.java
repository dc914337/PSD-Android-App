package anon.psd.crypto.protocol.packages.V2;

import anon.psd.crypto.protocol.packages.PackageTypeV2;
import anon.psd.crypto.protocol.packages.PackageV2;

/**
 * Created by dmitry on 2/13/16.
 */
public class PingPackage extends PackageV2
{
    public PingPackage()
    {
        type=PackageTypeV2.Ping;
    }
}
