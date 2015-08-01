package anon.psd.gui.activities;

import android.content.ComponentName;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.io.File;

import anon.psd.R;
import anon.psd.background.MessageTypes;
import anon.psd.background.PsdComService;
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


    /**
     * SERVICE STUFF
     */
    boolean serviceBound;
    /**
     * Messenger for communicating with service.
     */
    Messenger mService = null;
    /**
     * Handler of incoming messages from service.
     */
    final Messenger mMessenger = new Messenger(new ActivityHandler());
    private ServiceConnection mConnection;

    private class ActivityHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            MessageTypes type = MessageTypes.fromInteger(msg.what);
            switch (type) {
                /*case Connected:
                    Alerts.showMessage(getApplicationContext(), "PSD connected");
                    break;*/
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private class MyServiceConnection implements ServiceConnection
    {
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.d(TAG, "Activity onServiceConnected");
            mService = new Messenger(service);
            serviceBound = true;
            sendMessenger();
        }

        public void onServiceDisconnected(ComponentName name)
        {
            Log.d(TAG, "Activity onServiceDisconnected");
            mService = null;
            serviceBound = false;
        }
    }

    /*
        we are starting and binding service to have it alive all the time. It won't die when
        this activity will die. We sending fake intent
    */
    private void startService()
    {
        mConnection = new MyServiceConnection();
        Intent mServiceIntent = new Intent(this, PsdComService.class);
        //mServiceIntent.setData(Uri.parse("connect"));
        startService(mServiceIntent);
        bindService(mServiceIntent, mConnection, BIND_AUTO_CREATE);
    }

    private void sendMessenger()
    {
        Message msg = Message.obtain(null, MessageTypes.ConnectService.getInt());
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Activity Sent messenger to service");
    }

    private void sendCommandToService(MessageTypes msgType)
    {
        if (!serviceBound) {
            Log.e(TAG, "Activity Service is not bound");
            return;
        }

        Message msg = Message.obtain(null, msgType.getInt());
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, String.format("Activity Sent %s command", msgType.toString()));
    }


    /**
     * Activity events
     */
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService();
        initVariables();
        loadPasses();
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

        loadPasses();
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

        Intent mServiceIntent = new Intent(this, PsdComService.class);
        mServiceIntent.setData(Uri.parse("connect"));
        startService(mServiceIntent);
        bindService(mServiceIntent, mConnection, BIND_AUTO_CREATE);

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
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        return false;
    }


    private void loadPasses()
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
            return;
        }


        //check or set pass
        String userPass = prefs.getUserPass();
        if (userPass == null) {
            Alerts.showMessage(getApplicationContext(), "Set user pass");
            openEnterUserPassword();
            return;
        }

        //check or set path
        String dbPath = prefs.getDbPath();
        if (dbPath == null) {
            Alerts.showMessage(getApplicationContext(), "Set database path");
            openSettings();
            return;
        }

        //try load file
        if (!connectBase(dbPath)) {
            Alerts.showMessage(getApplicationContext(), "Can't access file or file doesn't exist");
            openSettings();
            return;
        }

        //try load base
        if (!loadBase(userPass)) {
            Alerts.showMessage(getApplicationContext(), "Password is incorrect or base is broken");
            prefs.setUserPass(null);//clear pass
            openEnterUserPassword();
            return;
        }

        //load passes from base
        passes = loadAndWrapPasses(baseRepo.getPassesBase().Passwords);
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


    private boolean loadBase(String userPass)
    {
        //check if user pass set
        if (userPass == null)
            return false;
        baseRepo.setUserPass(userPass);
        return baseRepo.update();
    }

    public void openEnterUserPassword()
    {
        Intent intent = new Intent(this, EnterPassActivity.class);
        startActivity(intent);
    }


}