package anon.psd.gui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;

import java.util.Date;

import anon.psd.R;
import anon.psd.gui.transfer.ActivitiesTransfer;
import anon.psd.models.PassItem;
import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 06.07.2015.
 */
public class PassActivity extends ActionBarActivity
{
    PrettyPassword prettyPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        prettyPassword = (PrettyPassword) ActivitiesTransfer.recieveTransferringObject("PRETTY_PASSWORD_ITEM");
        fillElements();
    }


    private void fillElements()
    {
        PassItem pass = prettyPassword.getPassItem();


        ((TextView) findViewById(R.id.txtTitle)).setText(pass.Title);
        ((TextView) findViewById(R.id.txtLogin)).setText(
                String.format("Login: %s", replaceNullOrEmpty(pass.Login, "-")));

        ((TextView) findViewById(R.id.txtWillBeEntered)).setText(
                pass.EnterWithLogin ? "(will be entered)" : "(will NOT be entered)");

        int timesEntered = prettyPassword.getHistory().size();
        ((TextView) findViewById(R.id.txtTimesEntered)).setText(
                String.format("Times entered: %s", String.valueOf(timesEntered)));


        Date lastEntered = prettyPassword.getHistory().getLastDate();
        ((TextView) findViewById(R.id.txtLastEntered)).setText(
                String.format("Last entered: %s", replaceNullOrEmpty(String.valueOf(lastEntered),
                                                  "was not entered yet")));

        ((TextView) findViewById(R.id.txtDescription)).setText(
                String.format("Description: %s", replaceNullOrEmpty(String.valueOf(lastEntered),
                        "-")));


    }

    private String replaceNullOrEmpty(String source, String replacement)
    {
        if (source == null || source.isEmpty() || source.equals("null"))
            return replacement;
        return source;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }


}
