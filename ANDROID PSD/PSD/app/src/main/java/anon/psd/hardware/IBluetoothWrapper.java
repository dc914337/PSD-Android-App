package anon.psd.hardware;

/**
 * Created by Dmitry on 03.08.2015.
 */
public interface IBluetoothWrapper
{
    public void enableBluetooth();
    public void disableBluetooth();

    public boolean tryConnectDevice();
    public void disconnectDevice();

    public boolean getConnected();
    public int getSignalStrength();

}
