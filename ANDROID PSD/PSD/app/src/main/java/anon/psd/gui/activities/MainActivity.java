package anon.psd.gui.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.io.File;
import java.util.Map;

import anon.psd.R;
import anon.psd.gui.adapters.PassItemsAdapter;
import anon.psd.gui.transfer.ActivitiesTransfer;
import anon.psd.models.AppearancesList;
import anon.psd.models.PassItem;
import anon.psd.models.PasswordList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.FileRepository;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    int debug_count = 0;
    FileRepository baseRepo;
    File appearanceCfgFile;

    PasswordList _passes;
    DynamicListView lvPasses;

    AppearancesList wrappedPasses;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_SHORT).show(); //debug

        //todo: remove debug code below
        //load appearance cfg
       /* AppearanceCfg appearanceCfg = new AppearanceCfg(new File(new ContextWrapper(this).getFilesDir().getPath(), "appearance.cfg"));
        appearanceCfg.update();

        PassItem fakePassItem = new PassItem();
        fakePassItem.Title = "sometitle2";
        PrettyPassword fakePrettyPassword = new PrettyPassword(fakePassItem);
        fakePrettyPassword.setPic(new File(Environment.getExternalStorageDirectory(), "home/psd/pic1.png"));
        appearanceCfg.getPassesAppearances().add(fakePrettyPassword);
        appearanceCfg.rewrite();*/
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
        Toast.makeText(getApplicationContext(), "Resumed " + debug_count++, Toast.LENGTH_SHORT).show(); //debug
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
        if (passesLoaded()) {
            //todo show message
            return;
        }

        if (!baseConnected()) {
            openSettings();
            //todo show message
            return;
        }

        String userPass = getUserPass();
        if (!loadBase(userPass)) {
            //todo show message
            return;
        }

        //load passes from base
        _passes = baseRepo.getPassesBase().Passwords;

        loadAndWrapPasses();
    }


    private void loadAndWrapPasses()
    {
        //getting wrapped passes if passes were not loaded(loading 2 files from disk)
        if (wrappedPasses == null)
            wrappedPasses = wrapPassesInAppearances(baseRepo.getPassesBase().Passwords);

        PassItemsAdapter adapter = new PassItemsAdapter<>(this, android.R.layout.simple_list_item_1, wrappedPasses);
        lvPasses.setAdapter(adapter);
    }


    private AppearancesList wrapPassesInAppearances(PasswordList passItems)
    {
        //load appearances
        AppearanceCfg appearanceCfg = new AppearanceCfg(appearanceCfgFile);
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

            currAppearance.loadPic();

            mergedAppearances.add(currAppearance);
        }

        return mergedAppearances;
    }


    private boolean passesLoaded()
    {
        return _passes != null;
    }


    /*
    Check if we can read file. doesn't mean we can decrypt it. Just read encrypted data
    */
    private boolean baseConnected()
    {
        if (baseRepo != null && baseRepo.update())
            return true;

        //get path from shared prefs
        String path = getPathFromSharedPrefs();

        if (path == null)
            return false;

        baseRepo = new FileRepository(path);

        return baseRepo.checkConnection();
    }

    private String getPathFromSharedPrefs()
    {
        SharedPreferences prefs = this.getSharedPreferences(
                getString(R.string.prefs_file), Context.MODE_PRIVATE);
        return prefs.getString("db_path", null);
    }

    private String getUserPass()
    {
        //todo: implement

        //-redirect to EnterUserPass page
        return "root";
    }


    private boolean loadBase(String userPass)
    {
        //todo: implement
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


    /*

        private void testStorage()
    {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory(), "testdir1");
// have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();


        File imgFile = new File(Environment.getExternalStorageDirectory(), "title.jpg");

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imageView);

            myImage.setImageBitmap(myBitmap);

        }

    }


     private void setPic()
        {
            ImageView testImgVies = (ImageView) findViewById(R.id.imageView);
            testImgVies.setImageResource(R.drawable.ic_launcher);
        }

        private void testMethod()
        {
            PrettyPassword.setDefaultPic(BitmapFactory.decodeResource(getResources(), R.drawable.default_key_pic));

            //load main base
            FileRepository repo = new FileRepository();
            repo.setUserPass("root");
            repo.setBasePath("/sdcard/home/psd/phone.psd");
            repo.update();

            DataBase passesBase = repo.getPassesBase();


            //load appearance cfg
            AppearanceCfg appearanceCfg = new AppearanceCfg(new File(Environment.getDataDirectory(), "appearance.cfg"));
            appearanceCfg.update();
            clean(appearanceCfg);
            appearanceCfg.update();

            //get prettyPasswords
            ArrayList<PrettyPassword> passesAppearances = appearanceCfg.getPassesAppearances();


    addFakeAppearances(passesAppearances);
appearanceCfg.rewrite();
        passesAppearances=appearanceCfg.getPassesAppearances();



        int i=0+1;
        //merge passwords with pretty passwords using title
        }


        private void clean(AppearanceCfg appearanceCfg)
        {
        ArrayList<PrettyPassword>passesAppearances=appearanceCfg.getPassesAppearances();
        passesAppearances.clear();
        appearanceCfg.rewrite();
        }

private void addFakeAppearances(ArrayList<PrettyPassword>passwords)
        {
        PassItem realPass=new PassItem();
        realPass.Id=1;
        realPass.Description="Description";
        realPass.EnterWithLogin=true;
        realPass.Login="Login";
        realPass.Pass="pass".getBytes();
        realPass.Title="title1";

        for(int i=0;i<3;i++){
        PrettyPassword currPPass=new PrettyPassword(realPass);
        currPPass.setPic(new File("pic"+i));
        passwords.add(currPPass);
        }
        }
        */