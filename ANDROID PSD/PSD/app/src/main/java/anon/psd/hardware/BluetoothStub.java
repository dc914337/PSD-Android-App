package anon.psd.hardware;

import java.util.Random;

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
    public void connectDevice(String mac)
    {
        randomSleep(1000, 1500);
        connected = trueRandom(80);
    }

    @Override
    public void disconnectDevice()
    {
        randomSleep(1000, 1500);
    }

    @Override
    public void sendPasswordBytes(byte[] passBytes)
    {
        randomSleep(1000, 1500);
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
