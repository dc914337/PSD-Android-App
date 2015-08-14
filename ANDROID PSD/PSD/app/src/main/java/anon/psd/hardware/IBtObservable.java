package anon.psd.hardware;

import anon.psd.device.ConnectionState;

/**
 * Created by Dmitry on 03.08.2015.
 */
public interface IBtObservable
{
    public void enableBluetooth();
    public void disableBluetooth();

    public boolean tryConnectDevice();

    public void disconnectDevice();


    public ConnectionState getConnectionState();


    public void registerObserver(IBtObserver listener);

    public void removeObserver();

}
