package anon.psd.gui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

import anon.psd.R;
import anon.psd.gui.fragments.SettingsFragment;
import anon.psd.gui.preferences.BtDeviceSelectorPreference;
import anon.psd.gui.preferences.PathPreference;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//I HATE CODE BELOW

    public static final int PATH_REQUEST_CODE = 1233;
    public static final int MAC_REQUEST_CODE = 1452;
    PathPreference pathPref;


    public void getPath(PathPreference pathPrefToSetValue)
    {
        pathPref = pathPrefToSetValue;
        Intent getContentIntent = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, PATH_REQUEST_CODE);
    }


    BtDeviceSelectorPreference btFinderPref;

    public void getMac(BtDeviceSelectorPreference btFinderPref)
    {
        this.btFinderPref = btFinderPref;
        Intent intent = new Intent(this, PairedPSDSelectorActivity.class);
        startActivityForResult(intent, MAC_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case PATH_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                        pathPref.setValue(file);
                    }
                }
                break;
            case MAC_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String name = data.getExtras().get(getString(R.string.extras_return_name)).toString();//Yay! I can't store 2 values using Preferences(extending Preferences)
                    String mac = data.getExtras().get(getString(R.string.extras_return_mac)).toString();
                    btFinderPref.setValue(mac);
                }
                break;
        }
        getPreferences(0);
    }


}
