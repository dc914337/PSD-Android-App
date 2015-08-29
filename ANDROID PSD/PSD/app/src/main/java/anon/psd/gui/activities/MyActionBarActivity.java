package anon.psd.gui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import anon.psd.R;
import anon.psd.gui.elements.LedController;

/**
 * Created by Dmitry on 29.08.2015.
 */
public abstract class MyActionBarActivity extends AppCompatActivity
{
    LedController ledController;

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

    /*public void openMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    public void exitClick(MenuItem item)
    {
        finish();
    }

    abstract void killService();

    public void killServiceClick(MenuItem item)
    {
        killService();
        exitClick(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
