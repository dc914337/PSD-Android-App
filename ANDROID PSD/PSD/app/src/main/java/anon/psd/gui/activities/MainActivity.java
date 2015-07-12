package anon.psd.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import anon.psd.R;
import anon.psd.models.gui.PrettyPassword;


public class MainActivity extends ActionBarActivity
{
    int debug_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_SHORT).show();
        loadBase();

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Resumed " + debug_count++, Toast.LENGTH_SHORT).show();
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


    private boolean loadBase()
    {
        //check if base path is set
        SharedPreferences prefs = this.getSharedPreferences(
                "anon.psd", Context.MODE_PRIVATE);
        String path = prefs.getString("db_path", null);

        //check if it exists
        //+load base
        //-redirect to setting to set path

        //check if user pass set
        //+load base using pass
        //-redirect to EnterUserPass page

        //check if loaded
        //- reset user password and start over

        return true;
    }


    public void openItem(PrettyPassword item)
    {
        Intent intent = new Intent(this, PassActivity.class);
        startActivity(intent);
    }


    public void openSettings(MenuItem item)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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







