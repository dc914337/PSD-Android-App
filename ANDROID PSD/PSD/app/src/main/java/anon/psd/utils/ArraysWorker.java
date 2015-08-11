package anon.psd.utils;

/**
 * Created by Dmitry on 11.08.2015.
 */
public class ArraysWorker
{
    public static byte[] concatArrays(byte[]... arrays)
    {
        int resLength = countArraysLength(arrays);

        byte[] result = new byte[resLength];

        int position = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, position, arr.length);
            position += arr.length;
        }

        return result;
    }

    private static int countArraysLength(byte[]... arrays)
    {
        int resLength = 0;
        for (byte[] arr : arrays) {
            resLength += arr.length;
        }
        return resLength;
    }
}
