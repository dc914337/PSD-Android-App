package anon.psd.background.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import anon.psd.background.messages.ErrorType;
import anon.psd.background.messages.RequestType;
import anon.psd.background.messages.ResponseMessageType;
import anon.psd.background.messages.ResponseType;
import anon.psd.background.service.PasswordForgetPolicyType;
import anon.psd.background.service.PsdService;
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
import anon.psd.models.PassItem;
import anon.psd.models.PasswordList;
import anon.psd.serializers.Serializer;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 * <p/>
 * This class works with PSD service. It needs activity only to bound service
 */
public abstract class PsdServiceWorker
{
    Activity activity;
    public boolean serviceBound;
    private int connectionTries = 0;
    private final int MAX_CONNECTION_TRIES = 2;
    private boolean autoconnect = false;


    public CurrentServiceState psdState = new CurrentServiceState();
    PasswordList passwordList = null;

    //Messenger for communicating with service.
    Messenger mService = null;

    //Handler of incoming messages from service.
    final Messenger mMessenger = new Messenger(new ActivityHandler());

    public PsdServiceWorker(Activity activity)
    {
        this.activity = activity;
    }

    /*
    we are starting and binding service to have it alive all the time. It won't die when
    this activity will die.
    */
    public void connectService()
    {
        ServiceConnection mConnection = new MyServiceConnection();
        Intent mServiceIntent = new Intent(activity, PsdService.class);
        activity.startService(mServiceIntent);
        activity.bindService(mServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }


    public void setAutoconnect(boolean value)
    {
        autoconnect = value;
        connectionTries = 0;
    }

    public void initService(String dbPath, byte[] dbPass, String psdMacAddress)
    {
        Bundle bundle = new Bundle();
        bundle.putString("DB_PATH", dbPath);
        bundle.putByteArray("DB_PASS", dbPass);
        bundle.putString("PSD_MAC_ADDRESS", psdMacAddress);
        Message msg = Message.obtain(null, RequestType.Init.getInt(), bundle);
        sendMessage(msg);
    }

    public void connectPsd(boolean persist, PasswordForgetPolicyType forgetPolicy)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("PERSIST", persist);
        bundle.putInt("FORGET_POLICY", forgetPolicy.getInt());
        Message msg = Message.obtain(null, RequestType.ConnectPSD.getInt(), bundle);
        sendMessage(msg);
    }

    public void disconnectPsd()
    {
        sendCommandToService(RequestType.DisconnectPSD);
    }

    public void sendPass(PassItem pass)
    {
        Bundle bundle = new Bundle();
        bundle.putShort("PASS_ITEM_ID", pass.id);
        Message msg = Message.obtain(null, RequestType.SendPass.getInt(), bundle);
        sendMessage(msg);
    }

    public void updateState()
    {
        sendCommandToService(RequestType.UpdateState);
    }

    public void rollKeys()
    {
        sendCommandToService(RequestType.RollKeys);
    }

    public void killService()
    {
        autoconnect = false;
        sendCommandToService(RequestType.Kill);
    }


    private void sendMessenger()
    {
        Message msg = Message.obtain(null, RequestType.ConnectService.getInt());
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendCommandToService(RequestType msgType)
    {
        Message msg = Message.obtain(null, msgType.getInt());
        sendMessage(msg);
    }

    private void sendMessage(Message msg)
    {
        if (!serviceBound) {
            Log(this, "[ ACTIVITY ] [ ERROR ] Service is not bound");
            return;
        }
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private class ActivityHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            ResponseType type = ResponseType.fromInteger(msg.what);
            switch (type) {
                case Message:
                    receivedMessage(msg);
                    break;
                case Error:
                    receivedError(msg);
                    break;
                case State:
                    receivedStateChanged(msg);
                    break;
                case PassesInfo:
                    receivedPassesInfo(msg);
                default:
                    super.handleMessage(msg);
            }
        }
    }


    private void receivedError(Message msg)
    {
        Bundle bundle = (Bundle) msg.obj;
        String message = bundle.getString("ERR_MSG");
        ErrorType type = ErrorType.fromInteger(bundle.getInt("ERR_TYPE"));

        onError(type, message);
    }

    private void receivedMessage(Message msg)
    {
        Bundle bundle = (Bundle) msg.obj;
        String message = bundle.getString("MSG_MSG");
        ResponseMessageType type = ResponseMessageType.fromInteger(bundle.getInt("MSG_TYPE"));
        switch (type) {
            case PassSentSuccess:
                onMessage(message);
                break;
        }
    }

    private void receivedPassesInfo(Message msg)
    {
        Bundle bundle = (Bundle) msg.obj;
        String info = bundle.getString("PASSES_INFO");
        passwordList = Serializer.deserializePasswordList(info);
        onPassesInfo(passwordList);
        processState();
    }

    private void receivedStateChanged(Message msg)
    {
        CurrentServiceState state = CurrentServiceState.fromByteArray(
                (byte[]) ((Bundle) msg.obj).get("SERVICE_STATE"));
        onStateChanged(state);
    }


    private class MyServiceConnection implements ServiceConnection
    {
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log(this, "[ ACTIVITY ] Service connected");
            mService = new Messenger(service);
            serviceBound = true;
            sendMessenger();
        }

        public void onServiceDisconnected(ComponentName name)
        {
            Log(this, "[ ACTIVITY ] Service disconnected");
            mService = null;
            serviceBound = false;
        }
    }


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
            showConnectionState(newState.getConnectionState());

        if (oldState == null || newState.getServiceState() != oldState.getServiceState())
            showServiceState(newState.getServiceState());

        if (oldState == null || newState.getProtocolState() != oldState.getProtocolState())
            showProtocolState(newState.getProtocolState());

        processState();
    }


    public void processState()
    {
        if (!serviceBound) {
            serviceNotConnected();
            return;
        }

        switch (psdState.getServiceState()) {
            case NotInitialised:
                serviceNotInitialised();
                break;
            case Initialised:
                serviceInitialised();
                break;
        }
    }

    private void serviceNotConnected()
    {
        connectService();
    }

    private void serviceNotInitialised()
    {
        //ask if data is ready
        String dbPath = getBasePath();
        String psdMac = getPSDMac();
        byte[] dbPass = getDbPass();

        if (dbPath != null
                && psdMac != null
                && dbPass != null)
            initService(dbPath, dbPass, psdMac);
    }

    private void serviceInitialised()
    {
        if (passwordList == null) {
            sendCommandToService(RequestType.GetPassesInfo);
        } else
            switch (psdState.getConnectionState()) {
                case Disconnected:
                    psdNotConnected();
                    break;
                case Connected:
                    psdConnected();
                    break;
            }
    }


    private void psdConnected()
    {
        connectionTries = 0;
        showConnectionState(psdState.getConnectionState());
        switch (psdState.getProtocolState()) {
            case ReadyToSend:
                break;
            case WaitingResponse:
                showProtocolState(psdState.getProtocolState());
                break;
        }
    }


    private void psdNotConnected()
    {
        if (connectionTries < MAX_CONNECTION_TRIES && autoconnect) {
            connectPsd(false, getPassForgetPolicy());
            connectionTries++;
        }
    }

    protected abstract String getBasePath();

    protected abstract byte[] getDbPass();

    protected abstract String getPSDMac();

    protected abstract PasswordForgetPolicyType getPassForgetPolicy();

    protected abstract void showProtocolState(ProtocolState protocolState);

    protected abstract void showServiceState(ServiceState serviceState);

    protected abstract void showConnectionState(ConnectionState connectionState);

    public abstract void onMessage(String msg);

    public abstract void onError(ErrorType err, String msg);

    public abstract void onPassesInfo(PasswordList info);

}
