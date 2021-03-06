package anon.psd.background.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MenuItem;

import anon.psd.R;
import anon.psd.background.messages.ErrorType;
import anon.psd.background.service.PasswordForgetPolicyType;

import anon.psd.communication.PSDCommunication;
import anon.psd.communication.PSDState;
import anon.psd.gui.activities.EnterPassActivity;
import anon.psd.gui.activities.RollbackActivity;
import anon.psd.gui.activities.SettingsActivity;
import anon.psd.gui.exchange.ActivitiesExchange;
import anon.psd.models.Strings;
import anon.psd.models.gui.PrettyDate;
import anon.psd.models.gui.PrettyPassword;
import anon.psd.notifications.Alerts;
import anon.psd.storage.PreferencesProvider;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 27.08.2015.
 */
public abstract class ActivitiesServiceWorker extends PsdServiceWorker
{
    private MenuItem connectionStateLed;
    private PrettyPassword lastEntered;
    private byte[] dbPass;
    private Context context;


    public ActivitiesServiceWorker(Activity activity)
    {
        super(activity);
        context=activity.getApplicationContext();
    }

    public void setConnectionStateLed(MenuItem connectionStateLed)
    {
        this.connectionStateLed = connectionStateLed;
        if (psdState != null)
            showPSDState(psdState);
    }


    public void sendPrettyPass(PrettyPassword prettyPassword)
    {
        lastEntered = prettyPassword;
        sendPass(prettyPassword.getPassItem());
    }




    protected void showPSDState(PSDState newState)
    {
        if(!isServiceInitialized)
        {
            whiteDot();
            return;
        }

        switch (newState) {
            case ReadyToSend:
                greenDot();
                break;
            case Awaiting:
                yellowDot();
                break;
            case Disconnected:
                redDot();
                break;
        }
    }


    private void whiteDot()
    {
        Log(this,"[DOT] [WHITE DOT]");
        if (connectionStateLed != null)
            connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_white));
    }

    private void redDot()
    {
        Log(this,"[DOT] [RED DOT]");
        if (connectionStateLed != null)
            connectionStateLed.setIcon(getPicFromRes(R.drawable.ic_little_red));

    }

    private void greenDot()
    {
        Log(this,"[DOT] [GREEN DOT]");
        if (connectionStateLed != null)
            connectionStateLed.setIcon(getPicFromRes(R.drawable.ic_little_green));
    }


    private void yellowDot()
    {
        Log(this,"[DOT] [YELLOW DOT]");
        if (connectionStateLed != null)
            connectionStateLed.setIcon(getPicFromRes(R.drawable.ic_little_yellow));
    }

    private Drawable getPicFromRes(int id)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return activity.getResources().getDrawable(id, context.getTheme());
        } else {
            return activity.getResources().getDrawable(id);
        }
    }


    public void openSettings()
    {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    public void openRollKeysMenu()
    {
        Intent intent = new Intent(activity, RollbackActivity.class);
        ActivitiesExchange.addObject("SERVICE_WORKER", this);
        activity.startActivity(intent);
    }


    public void openEnterUserPassword()
    {
        Intent intent = new Intent(activity, EnterPassActivity.class);
        ActivitiesExchange.addObject("PASSWORD_FORGET_POLICY", getPassForgetPolicy());
        activity.startActivity(intent);
    }


    /*
   * Returns true if all loaded without opening other activities
   * */
    protected String getBasePath()
    {
        PreferencesProvider prefs = new PreferencesProvider(activity);
        String basePath = prefs.getDbPath();
        if (basePath == null || basePath.isEmpty()) {
            Alerts.showMessage(activity, "Set database path");
            openSettings();
            return null;
        }
        return basePath;
    }

    protected byte[] getDbPass()
    {
        if (dbPass != null)
            return dbPass;

        if (getPassForgetPolicy() == PasswordForgetPolicyType.SavePassInPrefs) {
            PreferencesProvider prefs = new PreferencesProvider(activity);
            byte[] dbPass = prefs.getDbPass();
            if (dbPass == null || dbPass.length <= 0) {
                Alerts.showMessage(activity, "Set user pass");
                openEnterUserPassword();
                return null;
            }
            return dbPass;
        } else {
            dbPass = ActivitiesExchange.getAndRemoveObject("DB_PASSWORD");
            if (dbPass == null) {
                openEnterUserPassword();
                return null;// yeah, i know that i could return just dbPass, cuz it's null. but this case seems easier to understand
            }
            return dbPass;
        }

    }

    protected String getPSDMac()
    {
        PreferencesProvider prefs = new PreferencesProvider(activity);
        String psdMac = prefs.getPsdMacAddress();
        if (psdMac == null || psdMac.isEmpty()) {
            Alerts.showMessage(activity, "Set PSD");
            openSettings();
            return null;
        }
        return psdMac;
    }

    protected PasswordForgetPolicyType getPassForgetPolicy()
    {
        return new PreferencesProvider(activity).getPasswordForgetPolicyType();
    }


    @Override
    public void onMessage(String msg)
    {
        if (lastEntered != null)
            lastEntered.getHistory().add(new PrettyDate());
        Alerts.showMessage(activity, msg);
        passItemChanged();
        Log(this, "[ ACTIVITY ] Password sent successfully");
    }

    @Override
    public void onError(ErrorType err_type, String msg)
    {
        Alerts.showMessage(activity, msg);
        Log(this, "[ ACTIVITY ] [ ERROR ] Err type: %s \n " +
                "\t%s", err_type, msg);
        //process errors
        switch (err_type) {
            case IOError:
                disconnectPsd();
                break;
            case DBError:
                dbPass = null;
                openEnterUserPassword();
                break;
            case Desynchronization:
                openRollKeysMenu();
                break;
        }
    }

    abstract public void passItemChanged();
}