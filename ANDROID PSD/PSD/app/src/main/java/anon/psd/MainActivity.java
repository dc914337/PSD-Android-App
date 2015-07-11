package anon.psd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import anon.psd.models.DataBase;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.storage.AppearanceCfg;
import anon.psd.storage.FileRepository;


public class MainActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testStorage();

        setPic();


        //testMethod();
    }

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
        //load main base
        FileRepository repo = new FileRepository();
        repo.setUserPass("root");
        repo.setBasePath("/sdcard/home/psd/phone.psd");
        repo.update();

        DataBase passesBase = repo.getPassesBase();

        //load appearance cfg
        AppearanceCfg appearanceCfg = new AppearanceCfg(new File(Environment.getExternalStorageDirectory(), "appearance.cfg"));
        appearanceCfg.update();

        //get prettyPasswords
        ArrayList<PrettyPassword> passesAppearances = appearanceCfg.getPassesAppearances();

        //merge passwords with pretty passwords using title
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
}
