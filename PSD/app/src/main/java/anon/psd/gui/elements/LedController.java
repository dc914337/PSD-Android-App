package anon.psd.gui.elements;

import android.app.Activity;
import android.view.MenuItem;

import anon.psd.background.activity.ActivitiesServiceWorker;
import anon.psd.device.state.ConnectionState;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 26.08.2015.
 */
public class LedController
{
    ActivitiesServiceWorker serviceWorker;

    public LedController(ActivitiesServiceWorker serviceWorker)
    {
        this.serviceWorker = serviceWorker;
    }

    public void setLedView(MenuItem ledView)
    {
        serviceWorker.setConnectionStateLed(ledView);
    }
}
