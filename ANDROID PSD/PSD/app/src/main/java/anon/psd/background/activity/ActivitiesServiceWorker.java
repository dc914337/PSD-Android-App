package anon.psd.background.activity;

import android.view.MenuItem;

import anon.psd.R;
import anon.psd.background.messages.ErrorType;
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
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

    public void setConnectionStateLed(MenuItem connectionStateLed)
    {
        this.connectionStateLed = connectionStateLed;
        if (psdState != null)
            showConnectionState(psdState.getConnectionState());
    }


    public void sendPrettyPass(PrettyPassword prettyPassword)
    {
        lastEntered = prettyPassword;
        sendPass(prettyPassword.getPassItem());
    }


    protected void showServiceState(ServiceState newState)
    {
        switch (newState)
        {
            case NotInitialised:
                whiteDot();
                break;
        }
    }

    protected void showConnectionState(ConnectionState newState)
    {
        switch (newState)
        {
            case Disconnected:
                redDot();
                break;
            case Connected:
                greenDot();
                break;
        }
    }

    protected void showProtocolState(ProtocolState newState)
    {
        switch (newState)
        {
            case ReadyToSend:
                greenDot();
                break;
            case WaitingResponse:
                yellowDot();
                break;
        }
    }


    private void whiteDot()
    {
        if (connectionStateLed != null)
            connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_white));
    }

    private void redDot()
    {
        if (connectionStateLed != null)
            connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_red));
    }

    private void greenDot()
    {
        if (connectionStateLed != null)
            connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_green));
    }


    private void yellowDot()
    {
        if (connectionStateLed != null)
            connectionStateLed.setIcon(activity.getResources().getDrawable(R.drawable.ic_little_yellow));
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
        //do something if needed
        switch (err_type)
        {
            case IOError:
                disconnectPsd();
                break;
        }
    }

    abstract public void passItemChanged();
}