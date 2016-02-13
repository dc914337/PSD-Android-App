package anon.psd.hardware.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import anon.psd.background.messages.ErrorType;
import anon.psd.device.state.ConnectionState;
import anon.psd.hardware.bluetooth.lowlevelV1.BluetoothLowLevelProtocolV1;
import anon.psd.hardware.bluetooth.lowlevelV1.IBluetoothLowLevelProtocolV1;
import anon.psd.hardware.bluetooth.lowlevelV1.LowLevelMessageV1;
import anon.psd.utils.ArrayUtils;
import anon.psd.utils.DelayUtils;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 03.08.2015.
 */
public abstract class PsdBluetoothCommunication implements IBtObservable
{

    BTRegistrar btRegistrar;


    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket = null;
    OutputStream outStream = null;
    InputStream inStream = null;

    IBtObserver listener;

    Thread listenerThread;
    Thread liveCheckerThread;
    public static final int LIVE_CHECKER_SLEEP_MS = 300;


    //will not enable bluetooth immediately. It will wait for bt when will send first message
    public void enableBluetooth()
    {
        if (!btAdapter.isEnabled())
            btAdapter.enable();
    }

    public void disableBluetooth()
    {
        setConnectionState(ConnectionState.Disconnected);
        btAdapter.disable();
    }

    @Override
    public boolean isBluetoothEnabled()
    {
        return btAdapter.isEnabled();
    }


    public void setConnectionState(ConnectionState newConnectionState)
    {
        if (listener != null)
            listener.onConnectionStateChanged(newConnectionState); //send that state changed
    }

    public void sendError(ErrorType err, String errMessage)
    {
        if (listener != null)
            listener.sendError(err, errMessage); //send error
    }


    void errorHandler(ErrorType err, boolean disconnected, String errMsg, Object... args)
    {
        sendError(err, String.format(errMsg, args));
        errorHandler(disconnected, errMsg, args);
    }

    void errorHandler(boolean disconnected, String errMsg, Object... args)
    {
        Log(this, errMsg, args);
        if (disconnected)
            setConnectionState(ConnectionState.Disconnected);
    }

    @Override
    public void registerObserver(IBtObserver listener)
    {
        this.listener = listener;
        Log(this, "[ SERVICE ] Registered observer");
    }

    @Override
    public void removeObserver()
    {
        listener = null;
        Log(this, "[ SERVICE ] Removed observer");
    }

    private void stopListenForData()
    {
        if (listenerThread != null)
            listenerThread.interrupt();
    }

    private void stopLiveChecker()
    {
        if (liveCheckerThread != null)
            liveCheckerThread.interrupt();
    }


    public void stopPsdConnect()
    {
        Log(this, "[ SERVICE ] Stop PSD connect");
        stopListenForData();
        stopLiveChecker();
        btRegistrar = null;
    }

    @Override
    public abstract void connectDevice(String mac);

    @Override
    public abstract void sendPasswordBytes(byte[] passBytes);

}
