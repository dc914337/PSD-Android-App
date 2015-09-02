package anon.psd.gui.activities.actionbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import anon.psd.R;
import anon.psd.gui.activities.SettingsActivity;
import anon.psd.gui.elements.LedController;

/**
 * Created by Dmitry on 29.08.2015.
 */
public abstract class MyActionBarActivity extends AppCompatActivity
{
    public LedController ledController;

    /**
     * Menu entries
     */
    public void openSettingsClick(MenuItem item)
    {
        openSettings();
    }

    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public abstract void killService();

    public void killServiceClick(MenuItem item)
    {
        killService();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem ledConnected = menu.findItem(R.id.led_connected);
        ledController.setLedView(ledConnected);
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
        ledController.toggleStateIfStable();
    }


}
