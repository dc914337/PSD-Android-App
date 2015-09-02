package anon.psd.background.activity;

import android.app.Activity;
import android.view.MenuItem;

import anon.psd.R;
import anon.psd.background.messages.ErrorType;
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
import anon.psd.gui.exchange.ActivitiesExchange;
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
    public CurrentServiceState psdState = null;
    private MenuItem connectionStateLed;
    private PrettyPassword lastEntered;
    boolean changingActivity = false;

    public static ActivitiesServiceWorker getOrCreate(String key, ActivitiesServiceWorker newOne)
    {
        ActivitiesServiceWorker worker = ActivitiesExchange.getObject(key);
        if (worker == null)
            worker = newOne;
        return worker;
    }

    public void setConnectionStateLed(MenuItem connectionStateLed)
    {
        this.connectionStateLed = connectionStateLed;
        if (psdState != null)
            processConnectionState(psdState.getConnectionState(), true);
    }


    public void setNewActivity(Activity newActivity)
    {
        this.activity = newActivity;
        connectService();
        changingActivity = true;
    }

    public void sendPrettyPass(PrettyPassword prettyPassword)
    {
        lastEntered = prettyPassword;
        sendPass(prettyPassword.getPassItem());
    }


    @Override
    public void onStateChanged(CurrentServiceState newState)
    {
        Log(this,
                "[ Activity ] State changed.\n" +
                        "Service state: %s \n" +
                        "Connection state: %s \n" +
                        "Protocol state: %s",
                newState.getServiceState(),
                newState.getConnectionState(),
                newState.getProtocolState());

        CurrentServiceState oldState = psdState;
        psdState = newState;

        if (oldState == null || newState.getConnectionState() != oldState.getConnectionState())
            processConnectionState(newState.getConnectionState(), changingActivity);

        if (oldState == null || newState.getServiceState() != oldState.getServiceState())
            processServiceState(newState.getServiceState());

        if (oldState == null || newState.getProtocolState() != oldState.getProtocolState())
            processProtocolState(newState.getProtocolState());
        changingActivity = false;
    }


    private void processConnectionState(ConnectionState newState, boolean silent)
    {

        switch (newState) {
            case NotAvailable:
                if (connectionStateLed != null)
                    connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_white));
                break;
            case Disconnected:
                if (connectionStateLed != null)
                    connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_red));
                if (!silent)
                    Alerts.showMessage(activity, "PSD disconnected");
                break;
            case Connected:
                if (connectionStateLed != null)
                    connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_green));
                if (!silent)
                    Alerts.showMessage(activity, "PSD connected");
                break;
        }
    }


    private void processServiceState(ServiceState newState)
    {
        switch (newState) {
            case NotInitialised:
                PreferencesProvider prefs = new PreferencesProvider(activity);
                initService(prefs.getDbPath(), prefs.getDbPass(), prefs.getPsdMacAddress());
                break;
        }
    }

    private void processProtocolState(ProtocolState newState)
    {

    }


    @Override
    public void onPassSentSuccess()
    {
        if (lastEntered != null)
            lastEntered.getHistory().add(new PrettyDate());
        Alerts.showMessage(activity, "Password sent successfully");
        passItemChanged();
        Log(this, "[ ACTIVITY ] Password sent successfully");
    }

    @Override
    public void onError(ErrorType err_type, String msg)
    {
        Alerts.showMessage(activity, msg);
        Log(this, "[ ACTIVITY ] [ ERROR ] Err type: %s \n " +
                "\t%s", err_type, msg);
        //do something if needed
        switch (err_type) {
            case IOError:
                disconnectPsd();
                break;
        }
    }


    abstract public void passItemChanged();
}