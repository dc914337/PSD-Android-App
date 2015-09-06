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
import anon.psd.background.activity.ActivitiesServiceWorker;
import anon.psd.gui.activities.actionbar.MyActionBarActivity;
import anon.psd.gui.adapters.PassItemsAdapter;
import anon.psd.gui.elements.LedController;
import anon.psd.gui.exchange.ActivitiesExchange;
import anon.psd.models.AppearancesList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.notifications.Alerts;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.PreferencesProvider;

import static anon.psd.utils.DebugUtils.Log;


public class MainActivity extends MyActionBarActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    DynamicListView lvPasses;
    File appearanceCfgFile;
    AppearancesList passes;
    PassItemsAdapter adapter;
    AppearanceCfg appearanceCfg;
    ActivitiesServiceWorker serviceWorker;


    class MainActivitiesServiceWorker extends ActivitiesServiceWorker
    {
        @Override
        public void passItemChanged()
        {
            adapter.notifyDataSetChanged();
        }
    }

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
        getAllServiceInitData();
        connectService();
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

    private void connectService()
    {
        //load service worker
        serviceWorker = new MainActivitiesServiceWorker();
        serviceWorker.connectService(this);
        ledController = new LedController(this, serviceWorker);
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


    /*
    * Returns true if all loaded without opening other activities
    * */
    private boolean getAllServiceInitData()
    {
        PreferencesProvider prefs = new PreferencesProvider(this);
        String basePath = prefs.getDbPath();
        if (basePath == null || basePath.isEmpty()) {
            Alerts.showMessage(getApplicationContext(), "Set database path");
            openSettings();
            return false;
        }

        byte[] dbPass = prefs.getDbPass();
        if (dbPass == null || dbPass.length <= 0) {
            Alerts.showMessage(getApplicationContext(), "Set user pass");
            openEnterUserPassword();
            return false;
        }

        String psdMac = prefs.getPsdMacAddress();
        if (psdMac == null || psdMac.isEmpty()) {
            Alerts.showMessage(getApplicationContext(), "Set PSD");
            openSettings();
            return false;
        }

        //some other data to load
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

        bindAdapter();
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


    public void openEnterUserPassword()
    {
        Intent intent = new Intent(this, EnterPassActivity.class);
        startActivity(intent);
    }

    @Override
    public void killService()
    {
        serviceWorker.killService();
    }
}

