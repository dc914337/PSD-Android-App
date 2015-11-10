package anon.psd.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.Date;

import anon.psd.R;
import anon.psd.background.activity.ActivitiesServiceWorker;
import anon.psd.gui.activities.actionbar.MyActionBarActivity;
import anon.psd.gui.elements.LedController;
import anon.psd.gui.exchange.ActivitiesExchange;
import anon.psd.models.AppearancesList;
import anon.psd.models.PassItem;
import anon.psd.models.PasswordList;
import anon.psd.models.gui.PrettyPassword;

import static anon.psd.utils.DebugUtils.Log;
import static anon.psd.utils.TextUtils.replaceNullOrEmpty;

/**
 * Created by Dmitry on 06.07.2015.
 */
public class PassActivity extends MyActionBarActivity
{
    private static final int REQUEST_CODE = 1234;

    PrettyPassword prettyPassword;
    ImageView imgViewPic;
    PassActivitiesServiceWorker serviceWorker;

    ListView lstHistory;
    ArrayAdapter<?> adapter;

    class PassActivitiesServiceWorker extends ActivitiesServiceWorker
    {
        public PassActivitiesServiceWorker(Activity activity)
        {
            super(activity);
        }

        @Override
        public void passItemChanged()
        {
            fillElements();
        }

        @Override
        public void onPassesInfo(PasswordList info)
        {
            System.out.println();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log(this, "[ ACTIVITY ] [ CREATE ]");
        setContentView(R.layout.activity_password);
        AppearancesList passes = ActivitiesExchange.getObject("PASSES");
        short id = getIntent().getExtras().getShort("ID");
        prettyPassword = passes.findById(id);
        imgViewPic = ((ImageView) findViewById(R.id.imgIcon));
        fillElements();

        //load service worker
        serviceWorker = ActivitiesExchange.getObject("ACTIVITIES_SERVICE_WORKER");
        serviceWorker.connectService();
        ledController = new LedController(this, serviceWorker);

    }


    private void fillElements()
    {
        PassItem pass = prettyPassword.getPassItem();


        ((TextView) findViewById(R.id.txtTitle)).setText(pass.title);
        ((TextView) findViewById(R.id.txtLogin)).setText(
                String.format("Login: %s", replaceNullOrEmpty(pass.login, "-")));

        ((TextView) findViewById(R.id.txtWillBeEntered)).setText(
                pass.enterWithLogin ? "(will be entered)" : "(will NOT be entered)");

        int timesEntered = prettyPassword.getHistory().size();
        ((TextView) findViewById(R.id.txtTimesEntered)).setText(
                String.format("Times entered: %s", String.valueOf(timesEntered)));


        Date lastEntered = prettyPassword.getHistory().getLastDate();
        ((TextView) findViewById(R.id.txtLastEntered)).setText(
                String.format("Last entered: %s", replaceNullOrEmpty(String.valueOf(lastEntered),
                        "was not entered yet")));

        ((TextView) findViewById(R.id.txtDescription)).setText(
                String.format("Description: %s", replaceNullOrEmpty(pass.description,
                        "-")));

        lstHistory = (ListView) findViewById(R.id.lstHistory);

        imgViewPic.setImageBitmap(prettyPassword.getImage());
        bindAdapter();
        updateList();
    }

    private void bindAdapter()
    {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, prettyPassword.getHistory());
        lstHistory.setAdapter(adapter);
    }

    private void updateList()
    {
        adapter.notifyDataSetChanged();
        lstHistory.setSelection(adapter.getCount() - 1);
    }


    public void onPicClick(View view)
    {
        openSelectPicDialog();
    }

    private void openSelectPicDialog()
    {
        Intent getContentIntent = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void processSelectedPic(File picFile)
    {
        prettyPassword.setPicFromFile(picFile);
        imgViewPic.setImageBitmap(prettyPassword.getImage());
    }

    @Override
    public void killService()
    {
        serviceWorker.killService();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);
                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                        processSelectedPic(file);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBtnSendClick(View view)
    {
        serviceWorker.sendPrettyPass(prettyPassword);
    }
}
