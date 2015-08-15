package anon.psd.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import anon.psd.R;
import anon.psd.crypto.KeyGenerator;
import anon.psd.storage.PreferencesProvider;

/**
 * Created by Dmitry on 25.07.2015.
 */
public class EnterPassActivity extends Activity
{

    TextView txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        txtPass = (TextView) findViewById(R.id.txtPass);
    }


    public void btnOnClick(View view)
    {
        byte[] dbPass = KeyGenerator.getBasekeyFromUserkey(txtPass.getText().toString());
        new PreferencesProvider(this).setDbPass(dbPass);
        finish();
    }

    public void btnSettingsClick(View view)
    {
        openSettings();
    }

    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
