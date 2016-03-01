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

import anon.psd.communication.PSDState;
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
    final int SECONDS_TO_AUTODISCONNECT=6;

    private ServiceConnection mConnection;

    PSDState psdState;
    PasswordList passwordList = null;
    boolean isServiceInitialized=false;
    private boolean connectIfReady =false;

    //Messenger for communicating with service.
    Messenger mService = null;

    //Handler of incoming messages from service.
    final Messenger mMessenger = new Messenger(new ActivityHandler());

    public PsdServiceWorker(Activity activity)
    {
        this.activity = activity;
    }

    public void setConnectIfReady(boolean ConnectWhenReady)
    {
        connectIfReady =ConnectWhenReady;
    }
    /*
    we are starting and binding service to have it alive all the time. It won't die when
    this activity will die.
    */

    public void connectService()
    {
        mConnection = new MyServiceConnection();
        activity.startService(new Intent(activity, PsdService.class));
        serviceBound = activity.bindService(new Intent(activity, PsdService.class), mConnection, Context.BIND_AUTO_CREATE);
    }


    public void initService(String dbPath, byte[] dbPass, String psdMacAddress, PasswordForgetPolicyType forgetPolicy)
    {
        Bundle bundle = new Bundle();
        bundle.putString("DB_PATH", dbPath);
        bundle.putByteArray("DB_PASS", dbPass);
        bundle.putString("PSD_MAC_ADDRESS", psdMacAddress);
        bundle.putInt("FORGET_POLICY", forgetPolicy.getInt());
        Message msg = Message.obtain(null, RequestType.Init.getInt(), bundle);
        sendMessage(msg);
    }

    public void connectPsd(int autoDisconnectSeconds)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("AUTO_DISCONNECT_SECS",autoDisconnectSeconds);
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
        bundle.putShort("PASS_ITEM_ID", pass.getPsdId());
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

    public void unbind()
    {
        if(serviceBound)
          activity.unbindService(mConnection);
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
                case Initialized:
                    receivedInitState(msg);
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
        readyService();
    }

    private void receivedStateChanged(Message msg)
    {
        psdState = PSDState.fromInteger(
                (int) ((Bundle) msg.obj).get("COMM_STATE"));
        onStateChanged(psdState);
        if(connectIfReady)
        {
            connectPsd(SECONDS_TO_AUTODISCONNECT);
            connectIfReady=false;
        }
    }

    private void receivedInitState(Message msg)
    {
        isServiceInitialized =
                (boolean) ((Bundle) msg.obj).get("SERVICE_INITIALIZED");
        readyService();
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

    public void onStateChanged(PSDState newState)
    {
        psdState = newState;
        showPSDState(newState);
    }


    public void readyService(boolean connectAfter) {
        setConnectIfReady(connectAfter);
        readyService();
    }

    public void readyService()
    {

        if (!serviceBound) {
            connectService();
            return;
        }

        if(!isServiceInitialized)
        {
            initializeService();
             return;
        }
        
        if (passwordList == null) {
            sendCommandToService(RequestType.GetPassesInfo);
            return;
        }
        if(psdState==null)
        {
            updateState();
            return;
        }
    }

    private void initializeService()
    {
        //ask if data is ready
        String dbPath = getBasePath();
        String psdMac = getPSDMac();
        byte[] dbPass = getDbPass();

        if (dbPath != null
                && psdMac != null
                && dbPass != null)
            initService(dbPath, dbPass, psdMac, getPassForgetPolicy());
    }


    protected abstract String getBasePath();

    protected abstract byte[] getDbPass();

    protected abstract String getPSDMac();

    protected abstract PasswordForgetPolicyType getPassForgetPolicy();

    protected abstract void showPSDState(PSDState psdState);

    public abstract void onMessage(String msg);

    public abstract void onError(ErrorType err, String msg);

    public abstract void onPassesInfo(PasswordList info);

}
