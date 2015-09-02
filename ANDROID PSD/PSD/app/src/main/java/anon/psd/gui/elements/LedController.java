package anon.psd.gui.elements;

import android.app.Activity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.MenuItem;

import anon.psd.background.activity.ActivitiesServiceWorker;
import anon.psd.device.state.ConnectionState;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 26.08.2015.
 */
public class LedController
{
    Activity activity;
    boolean userWantsPsdOn;
    ActivitiesServiceWorker serviceWorker;

    public LedController(Activity activity, ActivitiesServiceWorker serviceWorker)
    {
        this.activity = activity;
        this.serviceWorker = serviceWorker;
        serviceWorker.setNewActivity(activity);
    }


    public void setState(boolean on)
    {
        userWantsPsdOn = on;
        setDesirablePsdState();
    }

    public void toggleState()
    {
        userWantsPsdOn = !userWantsPsdOn;
        setDesirablePsdState();
    }

    public void toggleStateIfStable()
    {
        if (userWantsPsdOn == serviceWorker.psdState.is(ConnectionState.Connected)) //if current state is what user wanted, then switch user desirable state
        {
            userWantsPsdOn = !userWantsPsdOn;
            Log(this, "[ ACTIVITY ] User wants PSD changed");
        }
        setDesirablePsdState();
    }

    private void setDesirablePsdState()
    {
        Log(this, "[ ACTIVITY ] User wants PSD on: %s", userWantsPsdOn);
        if (userWantsPsdOn && !serviceWorker.psdState.is(ConnectionState.Connected))
            serviceWorker.connectPsd(true);//persist
        else if (!userWantsPsdOn && serviceWorker.psdState.is(ConnectionState.Connected))
            serviceWorker.disconnectPsd();
    }

    public void setLedView(MenuItem ledView)
    {
        serviceWorker.setConnectionStateLed(ledView);
    }
}
