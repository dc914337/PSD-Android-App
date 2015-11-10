package anon.psd.gui.activities;

import android.app.Activity;
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
import anon.psd.models.PasswordList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.storage.AppearanceCfg;

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
        public MainActivitiesServiceWorker(Activity activity)
        {
            super(activity);
        }

        @Override
        public void passItemChanged()
        {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onPassesInfo(PasswordList passesInfo)
        {
            checkOrUpdateAppearanceCfg();
            passes = AppearancesList.Merge(passesInfo,
                    appearanceCfg.getPassesAppearances());
            bindAdapter();
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
        initService();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log(this, "[ ACTIVITY ] [ RESUME ]");
        serviceWorker.processState();
        checkOrUpdateAppearanceCfg();
        if (passes != null)
            bindAdapter();
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

    private void initService()
    {
        //load service worker
        serviceWorker = new MainActivitiesServiceWorker(this);
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
        ActivitiesExchange.addObject("ACTIVITIES_SERVICE_WORKER", serviceWorker);
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


    private void bindAdapter()
    {
        adapter = new PassItemsAdapter<>(this, android.R.layout.simple_list_item_1, passes);
        lvPasses.setAdapter(adapter);
    }


    private void checkOrUpdateAppearanceCfg()
    {
        //loading appearanceCfg
        appearanceCfg = new AppearanceCfg(appearanceCfgFile);

        AppearancesList prevPasses = ActivitiesExchange.getObject("PASSES");
        if (prevPasses != null)
            appearanceCfg.setPassesAppearances(prevPasses);
        else
            appearanceCfg.update();
    }


    @Override
    public void killService()
    {
        serviceWorker.killService();
    }
}

