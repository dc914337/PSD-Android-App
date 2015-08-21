package anon.psd.hardware.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import anon.psd.device.state.ConnectionState;
import anon.psd.hardware.bluetooth.lowlevel.BluetoothLowLevelProtocolV1;
import anon.psd.hardware.bluetooth.lowlevel.IBluetoothLowLevelProtocol;
import anon.psd.hardware.bluetooth.lowlevel.LowLevelMessage;
import anon.psd.utils.ArrayUtils;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdBluetoothCommunication implements IBtObservable
{
    public Date lastReceivedPong = new Date();
    public Date lastRequestWithoutResponse = new Date();

    //todo make thread safe
    public void updateTimeReceivedPong()
    {
        lastReceivedPong = new Date();
    }

    public void updateLastRequestWithoutResponse()
    {
        lastRequestWithoutResponse = new Date();
    }

    public void receivedResponse()
    {
        lastRequestWithoutResponse = null;
    }


    // SPP UUID сервиса
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket = null;
    OutputStream outStream = null;
    InputStream inStream = null;
    IBluetoothLowLevelProtocol lowLevelProtocol = new BluetoothLowLevelProtocolV1();
    IBtObserver listener;

    Thread listenerThread;
    Thread liveCheckerThread;


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
        setConnectionState(ConnectionState.NotConnected);
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
                e.printStackTrace();
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

        try {
            outStream.write(lowLevelProtocol.prepareConnectionMessage());
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        beginListenForData();
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
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btSocket = null;
        setConnectionState(ConnectionState.NotConnected);
        stopListenForData();
    }

    @Override
    public void sendPasswordBytes(byte[] passBytes)
    {
        updateLastRequestWithoutResponse();
        sendBytes(passBytes);
    }

    private boolean sendBytes(byte[] message)
    {
        try {
            outStream.write(lowLevelProtocol.prepareSendMessage(message));
            outStream.flush();
        } catch (IOException e) {
            setConnectionState(ConnectionState.NotConnected);
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void beginListenForData()
    {
        if (listenerThread != null && listenerThread.isAlive() && !listenerThread.isInterrupted())
            return;

        listenerThread = new Thread(new Runnable()
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
                        setConnectionState(ConnectionState.NotConnected);
                        stopWorker = true;
                    }

                    //start receiving if available
                    if (dataAvailable) {
                        LowLevelMessage received = lowLevelProtocol.receiveMessage(inStream);

                        switch (received.type) {
                            case Pong:
                                updateTimeReceivedPong();
                                setConnectionState(ConnectionState.Connected);
                                break;
                            case Response:
                                if (received.message != null) {
                                    receivedResponse();
                                    listener.onReceive(received);
                                }
                                break;
                            case Unknown:
                                Log.wtf("WTF", ArrayUtils.getHexArray(received.message));
                                break;
                        }
                    }

                }
            }
        });
        listenerThread.start();
    }

    private void stopListenForData()
    {
        listenerThread.interrupt();
    }


    private void beginLiveChecker()
    {
        if (liveCheckerThread != null && liveCheckerThread.isAlive() && !liveCheckerThread.isInterrupted())
            return;

        liveCheckerThread = new Thread(new Runnable()
        {
            public void run()
            {
                boolean stopWorker = false;
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                    //ping
                    //wait ping retry time
                    //check time in LastReceived
                    //set new state
                    //check last requestWithout receive
                    //send error
                }
            }
        });
        listenerThread.start();
    }

    private void stopLiveChecker()
    {
        liveCheckerThread.interrupt();
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
