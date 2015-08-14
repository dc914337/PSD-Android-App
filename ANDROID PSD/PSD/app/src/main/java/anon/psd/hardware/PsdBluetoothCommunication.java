package anon.psd.hardware;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import anon.psd.device.ConnectionState;

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
    InputStream inputStream = null;
    IBluetoothLowLevelProtocol lowLevelProtocol = new BluetoothLowLevelProtocolStub();
    ConnectionState conState = ConnectionState.Disconnected;

    IBtObserver listener;


    public PsdBluetoothCommunication()
    {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //will not enable bluetooth immediately. It will wait for bt when will send first message
    public void enableBluetooth()
    {
        btAdapter.enable();
    }

    public void disableBluetooth()
    {
        btAdapter.disable();
    }

    @Override
    public boolean tryConnectDevice()
    {
        return false;
    }

    public boolean tryConnectDevice(String mac)
    {
        //if bt not enabled - wait
        waitBtToEnable();

        //creating socket
        BluetoothDevice device = btAdapter.getRemoteDevice(mac);
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
            return false;
        }

        //getting output stream
        try {
            outStream = btSocket.getOutputStream();
            inputStream = btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        try {
            outStream.write(lowLevelProtocol.prepareConnectionMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        conState = ConnectionState.Connected;
        beginListenForData();
        return true;
    }


    public boolean sendData(byte[] message)
    {
        try {
            outStream.write(lowLevelProtocol.prepareSendMessage(message));
        } catch (IOException e) {
            conState = ConnectionState.Disconnected;
            e.printStackTrace();
            return false;
        }
        return false;
    }


    private void beginListenForData()
    {
        Thread workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                boolean stopWorker = false;
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    boolean dataAvailable = false;
                    //check if data available
                    try {
                        dataAvailable = inputStream.available() > 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                        conState = ConnectionState.Disconnected;
                        stopWorker = true;
                    }

                    //start receiving if available
                    if (dataAvailable) {
                        byte[] received = lowLevelProtocol.receiveMessage(inputStream);
                        if (received != null)
                            listener.onReceive(received);
                    }

                }
            }
        });

        workerThread.start();
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

    public void disconnectDevice()
    {
        try {
            outStream.write(lowLevelProtocol.prepareDisconnectMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        btSocket = null;
        conState = ConnectionState.Disconnected;

    }

    public ConnectionState getConnectionState()
    {
        return conState;
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

}
