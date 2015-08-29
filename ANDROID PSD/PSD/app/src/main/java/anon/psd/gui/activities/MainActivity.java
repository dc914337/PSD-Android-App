package anon.psd.gui.activities;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.io.File;

import anon.psd.R;
import anon.psd.background.ActivitiesServiceWorker;
import anon.psd.gui.adapters.PassItemsAdapter;
import anon.psd.gui.elements.LedController;
import anon.psd.gui.exchange.ActivitiesExchange;
import anon.psd.models.AppearancesList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.notifications.Alerts;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.FileRepository;
import anon.psd.storage.PreferencesProvider;

import static anon.psd.utils.DebugUtils.Log;


public class MainActivity extends MyActionBarActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    DynamicListView lvPasses;

    File appearanceCfgFile;

    FileRepository baseRepo;
    AppearancesList passes;
    PassItemsAdapter adapter;
    AppearanceCfg appearanceCfg;
    ActivitiesServiceWorker serviceWorker;


    /**
     * Activity events
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log(this, "[ ACTIVITY ] [ CREATE ]");
        setContentView(R.layout.activity_main);
        initVariables();

        if (tryLoadPasses())
            loadServiceWorker();
    }

    private void loadServiceWorker()
    {
        serviceWorker = ActivitiesServiceWorker.getOrCreate("ACTIVITIES_SERVICE_WORKER");
        serviceWorker.setActivity(this);
        serviceWorker.connectService();
        ledController = new LedController(this, serviceWorker);
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

        //load service worker
        serviceWorker = ActivitiesServiceWorker.getOrCreate("ACTIVITIES_SERVICE_WORKER");
        serviceWorker.setActivity(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (tryLoadPasses())
            serviceWorker.connectService();
        Log(this, "[ ACTIVITY ] [ RESUME ]");
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
        ActivitiesExchange.addObject("PASSES", passes);
        Intent intent = new Intent(getApplicationContext(), PassActivity.class);
        intent.putExtra("ID", item.getPassItem().id);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        PrettyPassword selectedPassWrapper = (PrettyPassword) adapterView.getItemAtPosition(position);
        serviceWorker.sendPrettyPass(selectedPassWrapper);
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

        loadAppearances();
        passes = AppearancesList.Merge(baseRepo.getPassesBase().passwords,
                appearanceCfg.getPassesAppearances());
        bindAdapter();
        return true;
    }


    private void loadAppearances()
    {
        //loading appearanceCfg
        appearanceCfg = new AppearanceCfg(appearanceCfgFile);

        AppearancesList prevPasses = ActivitiesExchange.getObject("PASSES");
        if (prevPasses != null)
            appearanceCfg.setPassesAppearances(prevPasses);
        else
            appearanceCfg.update();
    }


    private void bindAdapter()
    {
        adapter = new PassItemsAdapter<>(this, android.R.layout.simple_list_item_1, passes);
        lvPasses.setAdapter(adapter);
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

    @Override
    void killService()
    {
        serviceWorker.killService();
    }
}

