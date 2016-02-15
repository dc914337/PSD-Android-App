package anon.psd.utils;

import anon.psd.global.Constants;

/**
 * Created by dmitry on 2/13/16.
 */
public class ShortUtils {
    public static byte[] getShortBytes(short var)
    {
        byte[] res = new byte[Constants.INDEX_BYTES];
        int offset = 0;
        res[offset++] = (byte) (var >> 8);
        res[offset] = (byte) var;
        return res;
    }
}
