package anon.psd.hardware;

import java.util.Random;

/**
 * Created by Dmitry on 03.08.2015.
 */
public class BluetoothStub implements IBluetoothWrapper
{
    Random rnd = new Random();
    boolean connected = false;

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
    public boolean getConnected()
    {
        return connected;
    }

    @Override
    public int getSignalStrength()
    {
        randomSleep(1000, 1500);
        if (!connected)
            return 0;
        return properRandom(1, 100);
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
