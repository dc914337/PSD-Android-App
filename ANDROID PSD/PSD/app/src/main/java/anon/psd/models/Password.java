package anon.psd.models;

/**
 * Created by dmitry on 2/13/16.
 */
public class Password
{
    public byte[] bytes;

    public Password(byte[] passwordBytes) {
        bytes=passwordBytes;
    }
}
