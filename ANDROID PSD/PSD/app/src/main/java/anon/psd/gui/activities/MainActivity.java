package anon.psd.gui.activities;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.io.File;
import java.util.Map;

import anon.psd.R;
import anon.psd.gui.Alerts;
import anon.psd.gui.adapters.PassItemsAdapter;
import anon.psd.gui.transfer.ActivitiesTransfer;
import anon.psd.models.AppearancesList;
import anon.psd.models.PassItem;
import anon.psd.models.PasswordList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.FileRepository;
import anon.psd.storage.PreferencesProvider;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    FileRepository baseRepo;
    File appearanceCfgFile;

    AppearancesList passes;
    DynamicListView lvPasses;
    PassItemsAdapter adapter;
    AppearanceCfg appearanceCfg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    private void loadPasses()
    {
        //if passes are already loaded - skip loading and refresh representation
        if (passesLoaded()) {
            //refresh existing prettyPasses
            adapter.notifyDataSetChanged();
            saveChangedAppearances();
            return;
        }

        PreferencesProvider prefs = new PreferencesProvider(this);

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
        if (passes == null)
            wrappedPasses = wrapPassesInAppearances(passwords);

        adapter = new PassItemsAdapter<>(this, android.R.layout.simple_list_item_1, wrappedPasses);
        lvPasses.setAdapter(adapter);

        return wrappedPasses;
    }

    private void saveChangedAppearances()
    {
        appearanceCfg.setPassesAppearances(passes);
        appearanceCfg.rewrite();
    }

    private AppearancesList wrapPassesInAppearances(PasswordList passItems)
    {
        //load appearances
        appearanceCfg = new AppearanceCfg(appearanceCfgFile);
        appearanceCfg.update();
        AppearancesList loadedAppearances = appearanceCfg.getPassesAppearances();

        AppearancesList mergedAppearances = new AppearancesList();

        //merge appearances by title
        for (Map.Entry<Short, PassItem> entry : passItems.entrySet()) {
            PassItem currPass = entry.getValue();
            PrettyPassword currAppearance = loadedAppearances.findByTitle(currPass.Title);
            if (currAppearance == null)
                currAppearance = new PrettyPassword(currPass);
            else
                currAppearance.setPassItem(currPass);
            mergedAppearances.add(currAppearance);
        }

        return mergedAppearances;
    }


    private boolean passesLoaded()
    {
        return passes != null;
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

    /*
    Opens settings activity
    */
    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openEnterUserPassword()
    {
        Intent intent = new Intent(this, EnterPassActivity.class);
        startActivity(intent);
    }

    public void openSettingsClick(MenuItem item)
    {
        openSettings();
    }

    public void openItem(PrettyPassword item)
    {
        Intent intent = new Intent(this, PassActivity.class);
        ActivitiesTransfer.sendTransferringObject("PRETTY_PASSWORD_ITEM", item);
        startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        PrettyPassword item = (PrettyPassword) (lvPasses).getAdapter().getItem(position);
        openItem(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        return false;
    }

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
}