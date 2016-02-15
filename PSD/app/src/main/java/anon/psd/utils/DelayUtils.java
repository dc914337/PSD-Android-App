package anon.psd.utils;

import android.util.Log;

/**
 * Created by Dmitry on 22.08.2015.
 */
public class DelayUtils
{
    public static void threadSleep(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Log.wtf("WTF", "WOW! Thread sleep exception. ");
        }
    }

}
