package anon.psd.hardware;

import anon.psd.device.ConnectionStates;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class BluetoothWrap implements IBtObservable
{
    @Override
    public void enableBluetooth()
    {

    }

    @Override
    public void disableBluetooth()
    {

    }

    @Override
    public boolean tryConnectDevice()
    {
        return false;
    }

    @Override
    public void disconnectDevice()
    {

    }

    @Override
    public ConnectionStates getConnectionState()
    {
        return ConnectionStates.Connected;
    }

    @Override
    public void registerObserver(IBtObserver listener)
    {

    }

    @Override
    public void removeObserver()
    {

    }

    @Override
    public void notifyOnReceive(byte[] message)
    {

    }


    @Override
    public void notifyOnLowSignal()
    {

    }
}
