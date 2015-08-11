package anon.psd.hardware;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(byte[] message);
    void onLowSignal();
}
