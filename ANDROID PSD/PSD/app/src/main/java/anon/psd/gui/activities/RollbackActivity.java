package anon.psd.gui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import anon.psd.R;
import anon.psd.background.activity.PsdServiceWorker;
import anon.psd.gui.exchange.ActivitiesExchange;

/**
 * Created by Dmitry on 13.11.2015.
 */
public class RollbackActivity extends Activity
{
    PsdServiceWorker serviceWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rollback);
        serviceWorker = ActivitiesExchange.getAndRemoveObject("SERVICE_WORKER");
    }

    public void onDoNothingClick(View view)
    {
        finish();
    }

    public void onRollKeysClick(View view)
    {
        serviceWorker.rollKeys();
        finish();
    }
}
