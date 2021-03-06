package anon.psd.gui.activities.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import anon.psd.R;
import anon.psd.background.activity.ActivitiesServiceWorker;
import anon.psd.background.service.PasswordForgetPolicyType;
import anon.psd.gui.activities.MainActivity;
import anon.psd.gui.activities.SettingsActivity;
import anon.psd.gui.elements.LedController;
import anon.psd.models.PasswordList;
import anon.psd.storage.PreferencesProvider;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 29.08.2015.
 */
public abstract class PSDActivity extends AppCompatActivity
{
    public LedController ledController;
    MenuItem ledConnected;
    protected BarActivitiesServiceWorker serviceWorker;

    public void disconnectAndExitClick(MenuItem item) {
        serviceWorker.disconnectPsd();
        killActivities();
    }

    /**
     * Menu entries
     */

    protected class BarActivitiesServiceWorker extends ActivitiesServiceWorker
    {
        public BarActivitiesServiceWorker(Activity activity)
        {
            super(activity);
        }


        @Override
        public void passItemChanged()
        {
            PSDActivity.this.passItemChanged();
        }

        @Override
        public void onPassesInfo(PasswordList info)
        {
            PSDActivity.this.onPassesInfo(info);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        initService();
        Log(this, "[ ACTIVITY ] [ RESUME ]");
        serviceWorker.readyService(true);
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        serviceWorker.unbind();
    }



    private void initService()
    {
        //load service worker
        serviceWorker = new BarActivitiesServiceWorker(this);
        initLedController();
    }

    private void initLedController()
    {
        ledController = new LedController(serviceWorker);
        ledController.setLedView(ledConnected);
    }



    public void openSettingsClick(MenuItem item)
    {
        openSettings();
    }

    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public void killService()
    {
        PasswordForgetPolicyType forgetPolicy = new PreferencesProvider(this).getPasswordForgetPolicyType();
        if (serviceWorker != null && serviceWorker.serviceBound &&
                forgetPolicy == PasswordForgetPolicyType.OnAppClose) {
            serviceWorker.killService();
        }
    }

    private void killActivities()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public void killServiceClick(MenuItem item)
    {
        killService();
        killActivities();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ledConnected = menu.findItem(R.id.led_connected);
        initLedController();
        return true;
    }

    /**
     * Action bar
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onLedClick(MenuItem item)
    {
    }

    public abstract void passItemChanged();

    public abstract void onPassesInfo(PasswordList info);




}
