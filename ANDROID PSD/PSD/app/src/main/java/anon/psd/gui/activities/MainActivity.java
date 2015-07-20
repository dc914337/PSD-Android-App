package anon.psd.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import anon.psd.R;
import anon.psd.models.AppearancesList;
import anon.psd.models.PassItem;
import anon.psd.models.PasswordList;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.FileRepository;


public class MainActivity extends ActionBarActivity
{
    int debug_count = 0;
    FileRepository baseRepo;

    PasswordList _passes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_SHORT).show(); //debug
        //loadPasses();
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
            refreshPassesList();
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

        refreshPassesList();
    }


    private void refreshPassesList()
    {
        //todo: implement
        //here we got working passes list
        //load appearance cfg
        ArrayList<PrettyPassword> passesAppearances = wrapPassesInAppearances(baseRepo.getPassesBase().Passwords);
        //merge passwords with pretty passwords using title
    }


    private ArrayList<PrettyPassword> wrapPassesInAppearances(PasswordList passItems)
    {
        //load appearances
        AppearanceCfg appearanceCfg = new AppearanceCfg(new File(Environment.getDataDirectory(), "appearance.cfg"));
        appearanceCfg.update();
        AppearancesList loadedAppearances = appearanceCfg.getPassesAppearances();

        AppearancesList mergedAppearances = new AppearancesList();

        //merge appearances by title
        for (Map.Entry<Short, PassItem> entry : passItems.entrySet()) {
            PassItem currPass = entry.getValue();
            PrettyPassword currAppearance = loadedAppearances.findByTitile(currPass.Title);
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


    public void openItem(PrettyPassword item)
    {
        Intent intent = new Intent(this, PassActivity.class);
        startActivity(intent);
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


    addFakeAppearences(passesAppearances);
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

private void addFakeAppearences(ArrayList<PrettyPassword>passwords)
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







