package anon.psd.gui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.Date;

import anon.psd.R;
import anon.psd.gui.transfer.ActivitiesTransfer;
import anon.psd.models.PassItem;
import anon.psd.models.gui.PrettyPassword;

import static anon.psd.utils.TextUtils.replaceNullOrEmpty;

/**
 * Created by Dmitry on 06.07.2015.
 */
public class PassActivity extends ActionBarActivity
{
    private static final int REQUEST_CODE = 1234;

    PrettyPassword prettyPassword;
    ImageView imgViewPic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        prettyPassword = (PrettyPassword) ActivitiesTransfer.receiveTransferringObject("PRETTY_PASSWORD_ITEM");
        imgViewPic = ((ImageView) findViewById(R.id.imgIcon));
        fillElements();
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

        imgViewPic.setImageBitmap(prettyPassword.getImage());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }


}
