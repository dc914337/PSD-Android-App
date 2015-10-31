package anon.psd.hardware.bluetooth;

/**
 * Created by Dmitry on 03.08.2015.
 */
public interface IBtObservable
{
    public void enableBluetooth();

    public void disableBluetooth();

    public boolean isBluetoothEnabled();


    public enum BtConnectResult
    {
        ErrorCreatingSocket,
        ErrorConnectingDevice,
        ErrorGettingConnectionStream,
        Connected
    }
    public PsdBluetoothCommunication.BtConnectResult connectDevice(String mac);

    public void disconnectDevice();

    public void sendPasswordBytes(byte[] passBytes);

    public void registerObserver(IBtObserver listener);

    public void removeObserver();

}
