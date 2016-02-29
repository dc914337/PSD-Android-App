package anon.psd.hardware.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import anon.psd.background.messages.ErrorType;
import anon.psd.hardware.bluetooth.lowlevelV1.BluetoothLowLevelProtocolV1;
import anon.psd.hardware.bluetooth.lowlevelV1.IBluetoothLowLevelProtocolV1;
import anon.psd.hardware.bluetooth.lowlevelV1.LowLevelMessageV1;
import anon.psd.utils.ArrayUtils;
import anon.psd.utils.DelayUtils;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class PsdBluetoothProtocol implements IBtObservable
{
    BTRegistrar btRegistrar;

    // SPP UUID
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter btAdapter;
    BluetoothSocket btSocket = null;
    OutputStream outStream = null;
    InputStream inStream = null;
    IBluetoothLowLevelProtocolV1 lowLevelProtocol = new BluetoothLowLevelProtocolV1();
    IBtObserver observer;
    Thread listenerThread;
    boolean connected=false;

    @Override
    public boolean isConnected()
    {
        return connected;
    }

    private void setConnected(boolean connected)
    {
        if(this.connected!=connected)
        {
            this.connected=connected;
            if(connected)
                observer.onBtConnected();
            else
                observer.onBtDisconnected();
        }
    }



    public PsdBluetoothProtocol()
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
        btAdapter.disable();
        setConnected(false);
    }

    @Override
    public boolean isBluetoothEnabled()
    {
        return btAdapter.isEnabled();
    }

    void error(ErrorType err, String errMessage)
    {
        if (observer != null)
            observer.onBtError(err, errMessage); //send error
    }


    private void errorHandler(ErrorType err, boolean disconnected, String errMsg, Object... args)
    {
        error(err, String.format(errMsg, args));
        errorHandler(disconnected, errMsg, args);
    }

    private void errorHandler(boolean disconnected, String errMsg, Object... args)
    {
        Log(this, errMsg, args);
        if (disconnected)
            setConnected(false);
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
            errorHandler(true, "[ ERROR ] Error creating socket: %s", e.getMessage());
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
            errorHandler(true, "[ ERROR ] Error connecting device: %s", e.getMessage());
            return;
        }

        //getting output stream
        try {
            outStream = btSocket.getOutputStream();
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            errorHandler(true, "[ ERROR ] Error getting connection streams for device: %s", e.getMessage());
            return;
        }

        btRegistrar = new BTRegistrar();
        setConnected(true);
        sendPing();
        beginListenForData();
        //beginLiveChecker();
    }


    @Override
    public void registerObserver(IBtObserver listener)
    {
        this.observer = listener;
        Log(this, "[ SERVICE ] Registered observer");
    }

    @Override
    public void removeObserver()
    {
        observer = null;
        Log(this, "[ SERVICE ] Removed observer");
    }

    public void disconnectDevice()
    {
        stopPsdConnect();
        if(!isConnected())
            return;
        try {
            outStream.write(lowLevelProtocol.prepareDisconnectMessage());
            outStream.flush();
            outStream.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btSocket = null;
        outStream = null;
        inStream = null;
        setConnected(false);
    }


    public void stopPsdConnect()
    {
        Log(this, "[ SERVICE ] Stop PSD connect");
        stopListenForData();
        //stopLiveChecker();
        btRegistrar = null;
    }

    @Override
    public void sendPasswordBytes(byte[] passBytes)
    {
        if(!isConnected())
        return;
        btRegistrar.registerRequest();
        try {
            sendBytes(lowLevelProtocol.prepareSendMessage(passBytes));
        } catch (IOException e) {
            e.printStackTrace();
            errorHandler(true, "[ SERVICE ] [ ERROR ] Error sending message.Err message: %s", e.getMessage());
        }
    }

    public void sendPing()
    {
        if(!isConnected())
        return;

        btRegistrar.registerPing();
        try {
            sendBytes(lowLevelProtocol.preparePingMessage());
        } catch (IOException e) {
            e.printStackTrace();
            errorHandler(false, "[ SERVICE ] [ ERROR ] Error sending ping. Message: %s", e.getMessage());
        }
    }

    private synchronized void sendBytes(byte[] msg) throws IOException
    {
        outStream.write(msg);
        outStream.flush();
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

    private void beginListenForData()
    {
        if (listenerThread != null && listenerThread.isAlive() && !listenerThread.isInterrupted())
            return;

        listenerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while (!Thread.currentThread().isInterrupted()) {
                    boolean dataAvailable = false;
                    //check if data available
                    try {
                        dataAvailable = inStream.available() > 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                        setConnected(false);
                        break;
                    }

                    //start receiving if available
                    if (dataAvailable) {
                        LowLevelMessageV1 received = lowLevelProtocol.receiveMessage(inStream);

                        //receiveMessage is long running operation.
                        if (Thread.currentThread().isInterrupted())
                            return;

                        switch (received.type) {
                            case Pong:
                                btRegistrar.registerPong();
                                setConnected(true);
                                break;
                            case Response:
                                if (received.message != null) {
                                    btRegistrar.registerResponse();
                                    observer.onBtReceive(received);
                                }
                                break;
                            case Unknown:
                                if (received.message != null)
                                    Log(this, "[ SERVICE ] [ WTF ] Received strange message: %s", ArrayUtils.getHexArray(received.message));
                                else
                                    Log(this, "[ SERVICE ] [ WTF ] Received strange null message");
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
        if (listenerThread != null)
            listenerThread.interrupt();
    }






    //deprecated
    Thread liveCheckerThread;
    public static final int LIVE_CHECKER_SLEEP_MS = 300;
    private void beginLiveChecker()
    {
        if (liveCheckerThread != null && liveCheckerThread.isAlive() && !liveCheckerThread.isInterrupted())
            return;
        btRegistrar = new BTRegistrar();
        liveCheckerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while (!Thread.currentThread().isInterrupted()) {
                    //ping
                    if (btRegistrar != null && btRegistrar.pingReady())
                        sendPing();
                    //wait ping retry time
                    //check time in LastReceived
                    if (btRegistrar != null && btRegistrar.pongTimedOut()) {
                        //set disconnected state
                        Log(this, "[ SERVICE ] [ ERROR ] Pong timed out");
                        errorHandler(ErrorType.PongTimedOut, true, "No pong from PSD");
                        return;
                    }
                    //check last requestWithout receive
                    if (btRegistrar != null && btRegistrar.responseTimedOut()) {
                        //send error
                        errorHandler(ErrorType.Desynchronization, true, "Keys desynchronization");
                        return;
                    }

                    DelayUtils.threadSleep(LIVE_CHECKER_SLEEP_MS);
                }
            }
        });
        liveCheckerThread.start();
    }

    private void stopLiveChecker()
    {
        if (liveCheckerThread != null)
            liveCheckerThread.interrupt();
    }



}
