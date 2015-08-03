package anon.psd.hardware;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class BluetoothWrapper implements IBluetoothWrapper
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
    public boolean getConnected()
    {
        return false;
    }

    @Override
    public int getSignalStrength()
    {
        return 0;
    }
}
