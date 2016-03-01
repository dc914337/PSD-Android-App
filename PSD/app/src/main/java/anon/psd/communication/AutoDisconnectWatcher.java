package anon.psd.communication;

import java.util.Date;

/**
 * Created by Dmitry on 29/02/2016.
 */
public class AutoDisconnectWatcher implements Runnable {
    long disconnectMS;
    IAutoDisconnectListener listener;
    Date startTime;

    public AutoDisconnectWatcher(IAutoDisconnectListener listener,long disconnectSeconds)
    {
        this.disconnectMS =disconnectSeconds*1000;//seconds to ms
        this.listener=listener;
    }


    public synchronized void resetTimer()
    {
        startTime=new Date();
    }

    private void waitTime(long wait)
    {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized long countWaitTimeMS()
    {
        Date currDate=new Date();
        return currDate.getTime()-startTime.getTime();
    }


    @Override
    public void run() {
        resetTimer();
        while (!Thread.interrupted())
        {
            waitTime(countWaitTimeMS());
            if(countWaitTimeMS()>= disconnectMS)
            {
                listener.onAutoDisconnect();
                break;
            }
        }
    }
}
