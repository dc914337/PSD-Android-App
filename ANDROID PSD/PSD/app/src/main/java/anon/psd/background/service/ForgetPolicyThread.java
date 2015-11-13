package anon.psd.background.service;

import android.os.SystemClock;

import java.util.Date;

/**
 * Created by Dmitry on 13.11.2015.
 */
public class ForgetPolicyThread extends Thread
{
    private PasswordForgetPolicyType policy;
    private final int CHECK_PERIOD_MILLIS = 1000;
    private final int DELAY_30_MINUTES_IN_MILLIS = /*30 * 60 */ 5000;

    private PsdService serviceToKill;

    private long passRememberedMs;

    public ForgetPolicyThread(PasswordForgetPolicyType checkPolicy, Date started, PsdService serviceToKill)
    {
        policy = checkPolicy;
        passRememberedMs = started.getTime();
        this.serviceToKill = serviceToKill;
    }

    @Override
    public void run()
    {
        switch (policy) {
            case After30Minutes:
                rememberPass30Minutes();
                break;
        }
    }


    private void rememberPass30Minutes()
    {
        while (!currentThread().isInterrupted()) {
            SystemClock.sleep(CHECK_PERIOD_MILLIS);
            if (passRememberedMs + DELAY_30_MINUTES_IN_MILLIS < new Date().getTime()) {
                serviceToKill.stopStoringPassword();
            }
        }

    }
}
