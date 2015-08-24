package anon.psd.hardware.bluetooth;

import java.util.Date;

import anon.psd.utils.TimeUtils;

/**
 * Created by Dmitry on 22.08.2015.
 */
//todo make thread safe
public class BTRegistrar
{
    public static final int RETRY_PING_MS = 1000;//4000
    public static final int PONG_TIMEOUT = 200000;//6000
    public static final int RESPONSE_TIMEOUT = 15000;//5000

    private Date lastSentPing = null;
    private Date lastReceivedPong = null;
    private Date lastRequestWithoutResponse = null;


    public void registerPing()
    {
        lastSentPing = new Date();
    }

    public void registerPong()
    {
        lastReceivedPong = new Date();
    }

    public void registerRequest()
    {
        lastRequestWithoutResponse = new Date();
    }

    public void registerResponse()
    {
        lastRequestWithoutResponse = null;
    }


    public boolean pingReady()
    {
        Date now = new Date();
        if (lastSentPing == null)
            return true;
        return TimeUtils.msBetweenDates(now, lastSentPing) > RETRY_PING_MS;
    }

    public boolean pongTimedOut()
    {
        Date now = new Date();
        if (lastReceivedPong == null)
            return false;
        return TimeUtils.msBetweenDates(now, lastReceivedPong) > PONG_TIMEOUT;
    }

    public boolean responseTimedOut()
    {
        Date now = new Date();
        if (lastRequestWithoutResponse == null)
            return false;
        return TimeUtils.msBetweenDates(now, lastRequestWithoutResponse) > RESPONSE_TIMEOUT;
    }


}
