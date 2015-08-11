package anon.psd.hardware;

import java.util.Random;

import anon.psd.device.ConnectionStates;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class BluetoothStub implements IBtObservable
{
    Random rnd = new Random();
    boolean connected = false;
    IBtObserver listener;


    @Override
    public void enableBluetooth()
    {
        randomSleep(2500, 4000);
    }

    @Override
    public void disableBluetooth()
    {
        randomSleep(1000, 1500);
    }

    @Override
    public boolean tryConnectDevice()
    {
        randomSleep(1000, 1500);
        connected = trueRandom(80);
        return connected;
    }

    @Override
    public void disconnectDevice()
    {
        randomSleep(1000, 1500);
    }

    @Override
    public ConnectionStates getConnectionState()
    {
        return ConnectionStates.Connected;
    }

    @Override
    public void registerObserver(IBtObserver newListener)
    {
        listener = newListener;
    }

    @Override
    public void removeObserver()
    {
        listener = null;
    }

    @Override
    public void notifyOnReceive(byte[] message)
    {
        if (listener != null)
            listener.onReceive(message);
    }

    @Override
    public void notifyOnLowSignal()
    {
        if (listener != null)
            listener.onLowSignal();
    }

    private void randomSleep(int from, int to)
    {
        try {
            Thread.sleep(properRandom(from, to));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int properRandom(int from, int to)
    {
        return from + rnd.nextInt(to - from);
    }

    private boolean trueRandom(int percentTrue)
    {
        int randomNumber = rnd.nextInt(100);
        return randomNumber < percentTrue;
    }
}
