package anon.psd.hardware;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import anon.psd.device.ConnectionState;
import anon.psd.utils.ArrayUtils;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdBluetoothCommunication implements IBtObservable
{
    // SPP UUID сервиса
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket = null;
    OutputStream outStream = null;
    InputStream inStream = null;
    IBluetoothLowLevelProtocol lowLevelProtocol = new BluetoothLowLevelProtocolV1();
    ConnectionState connectionState = ConnectionState.Disconnected;

    IBtObserver listener;


    public PsdBluetoothCommunication()
    {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

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

    public void setConnectionState(ConnectionState newConnectionState)
    {
        if (newConnectionState != connectionState) {
            listener.onStateChanged(newConnectionState); //send that state changed
        }

        //do specific shit
        switch (newConnectionState) {
            case Disconnected:
                stopListenForData();
                break;
        }

        connectionState = newConnectionState;
    }

    @Override
    public void connectDevice(String mac)
    {
        //if bt not enabled - wait
        waitBtToEnable();

        //creating socket
        BluetoothDevice device = btAdapter.getRemoteDevice(mac);
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        btAdapter.cancelDiscovery();//just in case cuz discovery is resource intensive


        //connecting
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                e.printStackTrace();
            }
            return;
        }

        //getting output stream
        try {
            outStream = btSocket.getOutputStream();
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        beginListenForData();
        try {
            outStream.write(lowLevelProtocol.prepareConnectionMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }


    @Override
    public void registerObserver(IBtObserver listener)
    {
        this.listener = listener;
    }

    @Override
    public void removeObserver()
    {
        listener = null;
    }

    public void disconnectDevice()
    {
        try {
            outStream.write(lowLevelProtocol.prepareDisconnectMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        btSocket = null;
        setConnectionState(ConnectionState.Disconnected);
        stopListenForData();
    }

    @Override
    public void sendPasswordBytes(byte[] passBytes)
    {
        sendBytes(passBytes);
    }

    private boolean sendBytes(byte[] message)
    {
        try {
            outStream.write(lowLevelProtocol.prepareSendMessage(message));
        } catch (IOException e) {
            setConnectionState(ConnectionState.Disconnected);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    Thread workerThread;

    private void beginListenForData()
    {
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                boolean stopWorker = false;
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    boolean dataAvailable = false;
                    //check if data available
                    try {
                        dataAvailable = inStream.available() > 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                        setConnectionState(ConnectionState.Disconnected);
                        stopWorker = true;
                    }

                    //start receiving if available
                    if (dataAvailable) {
                        LowLevelMessage received = lowLevelProtocol.receiveMessage(inStream);

                        switch (received.type) {
                            case Pong:
                                setConnectionState(ConnectionState.Connected);
                                break;
                            case Response:
                                if (received.message != null)
                                    listener.onReceive(received);
                                break;
                            case Unknown:
                                Log.wtf("WTF", ArrayUtils.getHexArray(received.message));
                                break;
                        }
                    }

                }
            }
        });

        workerThread.start();
    }

    private void stopListenForData()
    {
        workerThread.interrupt();
    }


    private void waitBtToEnable()
    {
        while (!btAdapter.isEnabled()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
