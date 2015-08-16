package anon.psd.gui.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.io.File;

import anon.psd.R;
import anon.psd.background.PSDServiceWorker;
import anon.psd.device.ConnectionState;
import anon.psd.gui.adapters.PassItemsAdapter;
import anon.psd.gui.transfer.ActivitiesTransfer;
import anon.psd.models.AppearancesList;
import anon.psd.models.PasswordList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.notifications.Alerts;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.FileRepository;
import anon.psd.storage.PreferencesProvider;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private static final String TAG = "MainActivity";

    DynamicListView lvPasses;

    File appearanceCfgFile;

    FileRepository baseRepo;
    AppearancesList passes;
    PassItemsAdapter adapter;
    AppearanceCfg appearanceCfg;

    PSDServiceWorker serviceWorker;

    boolean PsdConnected = false;


    private class MainPSDServiceWorker extends PSDServiceWorker
    {
        public MainPSDServiceWorker(Context context)
        {
            super(context);
        }


        @Override
        public void onStateChanged(ConnectionState newState)
        {
            ActionMenuItemView connectionStateLed = (ActionMenuItemView) findViewById(R.id.led_connected);
            Log.d(TAG, String.format("Activity State changed on %s", newState));

            switch (newState) {
                case Connected:
                    connectionStateLed.setIcon(getResources().getDrawable(R.drawable.ic_little_green));
                    PsdConnected = true;
                    break;
                case Disconnected:
                    connectionStateLed.setIcon(getResources().getDrawable(R.drawable.ic_little_red));
                    PsdConnected = false;
                    break;
            }
        }

        @Override
        public void onReceivedResult(boolean res)
        {
            Log.d(TAG, String.format("Activity Received result %s", res));
        }

    }


    /**
     * Activity events
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceWorker = new MainPSDServiceWorker(this);
        initVariables();
        tryLoadPasses();
        connectAndInitService();
    }


    private void connectAndInitService()
    {
        PreferencesProvider prefs = new PreferencesProvider(this);
        serviceWorker.connectService(prefs.getDbPath(), prefs.getDbPass(), prefs.getPsdMacAddress());
    }

    private void initVariables()
    {
        //load path to appearance.cfg file
        appearanceCfgFile = new File(new ContextWrapper(this).getFilesDir().getPath(), "appearance.cfg");

        //init passes list element
        lvPasses = (DynamicListView) findViewById(R.id.lvPassesList);
        lvPasses.setOnItemClickListener(this);
        lvPasses.setOnItemLongClickListener(this);


        //set default pic for passes
        PrettyPassword.setDefaultPic(BitmapFactory.decodeResource(getResources(), R.drawable.default_key_pic));
        PrettyPassword.setPicsDir(new File(new ContextWrapper(this).getFilesDir().getPath(), "pics"));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        tryLoadPasses();
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


    public void onConnectPsdClick(MenuItem item)
    {
        if (!PsdConnected)
            serviceWorker.connectPsd();
        else
            serviceWorker.disconnectPsd();
    }

    /**
     * Search
     */
    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        return false;
    }


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


    /**
     * Items
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        PrettyPassword item = (PrettyPassword) (lvPasses).getAdapter().getItem(position);
        openItem(item);
    }

    public void openItem(PrettyPassword item)
    {
        Intent intent = new Intent(this, PassActivity.class);
        ActivitiesTransfer.sendTransferringObject("PRETTY_PASSWORD_ITEM", item);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        PrettyPassword selectedPassWrapper = (PrettyPassword) adapterView.getItemAtPosition(position);
        serviceWorker.sendPass(selectedPassWrapper.getPassItem());
        return true;
    }

    private boolean tryLoadPasses()
    {
        PreferencesProvider prefs = new PreferencesProvider(this);

        //if changed base path then refresh all
        if (baseRepo != null &&
                !baseRepo.getBasePath().equals(prefs.getDbPath())) {
            passes = null;
        }

        //if passes are already loaded - skip loading and refresh representation and base path is the same
        if (passes != null) {
            //refresh existing prettyPasses
            adapter.notifyDataSetChanged();
            saveChangedAppearances();
            return false;
        }
        //check or set pass
        byte[] dbPass = prefs.getDbPass();
        if (dbPass == null || dbPass.length <= 0) {
            Alerts.showMessage(getApplicationContext(), "Set user pass");
            openEnterUserPassword();
            return false;
        }

        //check or set path
        String dbPath = prefs.getDbPath();
        if (dbPath == null) {
            Alerts.showMessage(getApplicationContext(), "Set database path");
            openSettings();
            return false;
        }

        //try load file
        if (!connectBase(dbPath)) {
            Alerts.showMessage(getApplicationContext(), "Can't access file or file doesn't exist");
            openSettings();
            return false;
        }

        //try load base
        if (!loadBase(dbPass)) {
            Alerts.showMessage(getApplicationContext(), "Password is incorrect or base is broken");
            prefs.setDbPass(null);//clear pass
            openEnterUserPassword();
            return false;
        }
        //load passes from base
        passes = loadAndWrapPasses(baseRepo.getPassesBase().Passwords);
        return true;
    }

    private AppearancesList loadAndWrapPasses(PasswordList passwords)
    {
        AppearancesList wrappedPasses = null;
        //getting wrapped passes if passes were not loaded(loading 2 files from disk)
        if (passes == null) {
            //loading appearanceCfg
            appearanceCfg = new AppearanceCfg(appearanceCfgFile);
            appearanceCfg.update();
            //merging passes and loaded appearances
            wrappedPasses = AppearancesList.Merge(passwords, appearanceCfg.getPassesAppearances());
        }

        adapter = new PassItemsAdapter<>(this, android.R.layout.simple_list_item_1, wrappedPasses);
        lvPasses.setAdapter(adapter);

        return wrappedPasses;
    }

    private void saveChangedAppearances()
    {
        appearanceCfg.setPassesAppearances(passes);
        appearanceCfg.rewrite();
    }

    /*
    Check if we can read file. doesn't mean we can decrypt it. Just read encrypted data
    */
    private boolean connectBase(String path)
    {
        baseRepo = new FileRepository(path);
        return baseRepo.checkConnection();
    }

    private boolean loadBase(byte[] dbPass)
    {
        //check if user pass set
        if (dbPass == null)
            return false;
        baseRepo.setDbPass(dbPass);
        return baseRepo.update();
    }

    public void openEnterUserPassword()
    {
        Intent intent = new Intent(this, EnterPassActivity.class);
        startActivity(intent);
    }
}