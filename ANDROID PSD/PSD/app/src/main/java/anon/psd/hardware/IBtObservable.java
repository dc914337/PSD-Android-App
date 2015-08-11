package anon.psd.hardware;

import anon.psd.device.ConnectionStates;

/**
 * Created by Dmitry on 03.08.2015.
 */
public interface IBtObservable
{
    public void enableBluetooth();
    public void disableBluetooth();


    public boolean tryConnectDevice();

    public void disconnectDevice();

    public ConnectionStates getConnectionState();

    public void registerObserver(IBtObserver listener);

    public void removeObserver();


    public void notifyOnReceive(byte[] message);
    public void notifyOnLowSignal();
}
