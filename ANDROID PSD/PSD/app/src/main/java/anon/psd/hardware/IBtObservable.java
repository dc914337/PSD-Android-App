package anon.psd.hardware;

/**
 * Created by Dmitry on 03.08.2015.
 */
public interface IBtObservable
{
    public void enableBluetooth();

    public void disableBluetooth();

    public void connectDevice(String mac);

    public void disconnectDevice();

    public void sendPasswordBytes(byte[] passBytes);

    public void registerObserver(IBtObserver listener);

    public void removeObserver();

}
