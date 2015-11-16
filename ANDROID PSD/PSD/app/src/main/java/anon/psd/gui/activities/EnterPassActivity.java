package anon.psd.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import anon.psd.R;
import anon.psd.background.service.PasswordForgetPolicyType;
import anon.psd.crypto.KeyGenerator;
import anon.psd.gui.exchange.ActivitiesExchange;
import anon.psd.storage.PreferencesProvider;

/**
 * Created by Dmitry on 25.07.2015.
 */
public class EnterPassActivity extends Activity
{
    TextView txtPass;
    PasswordForgetPolicyType forgetPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        txtPass = (TextView) findViewById(R.id.txtPass);
        forgetPolicy = ActivitiesExchange.getObject("PASSWORD_FORGET_POLICY");
    }


    public void btnOnClick(View view)
    {
        byte[] dbPass = KeyGenerator.getBasekeyFromUserkey(txtPass.getText().toString());
        if (forgetPolicy == PasswordForgetPolicyType.SavePassInPrefs) {
            new PreferencesProvider(this).setDbPass(dbPass);
        } else {
            ActivitiesExchange.addObject("DB_PASSWORD", dbPass);
        }
        finish();
    }

    public void btnSettingsClick(View view)
    {
        openSettings();
    }

    public void btnExitClick(View view)
    {
        killActivities();
    }

    //COPY-PASTE CODE
    private void killActivities()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }


    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
