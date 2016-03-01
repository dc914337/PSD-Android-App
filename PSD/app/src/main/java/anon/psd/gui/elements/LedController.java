package anon.psd.gui.elements;

import android.view.MenuItem;

import anon.psd.background.activity.ActivitiesServiceWorker;



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
        if(serviceWorker!=null)
            serviceWorker.setConnectionStateLed(ledView);
    }
}
