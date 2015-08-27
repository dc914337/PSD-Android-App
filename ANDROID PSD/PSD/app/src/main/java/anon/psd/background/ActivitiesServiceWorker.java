package anon.psd.background;

import android.app.Activity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.View;

import anon.psd.R;
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
import anon.psd.gui.exchange.ActivitiesExchange;
import anon.psd.notifications.Alerts;
import anon.psd.storage.PreferencesProvider;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 27.08.2015.
 */
public class ActivitiesServiceWorker extends PsdServiceWorker
{
    public CurrentServiceState psdState = null;
    ActionMenuItemView connectionStateLed;

    public static ActivitiesServiceWorker getOrCreate(String key)
    {
        ActivitiesServiceWorker worker = ActivitiesExchange.getObject(key);
        if (worker == null)
            worker = new ActivitiesServiceWorker();
        return worker;
    }


    public void setNewActivity(Activity newActivity)
    {
        this.activity = newActivity;
        connectService();
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
            connectionStateChanged(newState.getConnectionState());

        if (oldState == null || newState.getServiceState() != oldState.getServiceState())
            serviceStateChanged(newState.getServiceState());

        if (oldState == null || newState.getProtocolState() != oldState.getProtocolState())
            protocolStateChanged(newState.getProtocolState());
    }


    private void connectionStateChanged(ConnectionState newState)
    {
        if (connectionStateLed == null) {
            View ledConnected = activity.findViewById(R.id.led_connected);
            connectionStateLed = (ActionMenuItemView) ledConnected;
        }

        switch (newState) {
            case NotAvailable:
                connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_green));
                break;
            case Disconnected:
                connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_red));
                Alerts.showMessage(activity, "PSD disconnected");
                break;
            case Connected:
                connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_green));
                Alerts.showMessage(activity, "PSD connected");
                break;
        }
    }


    private void serviceStateChanged(ServiceState newState)
    {
        switch (newState) {
            case NotInitialised:
                PreferencesProvider prefs = new PreferencesProvider(activity);
                initService(prefs.getDbPath(), prefs.getDbPass(), prefs.getPsdMacAddress());
                break;
        }
    }

    private void protocolStateChanged(ProtocolState newState)
    {

    }


    @Override
    public void onPassSentSuccess()
    {
        Alerts.showMessage(activity, "Password sent successfully");
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
}