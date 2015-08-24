package anon.psd.background;

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
import android.util.Log;

import anon.psd.device.state.CurrentServiceState;
import anon.psd.models.PassItem;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 */
public abstract class PSDServiceWorker
{
    private final String TAG = "ServiceWorker";
    Context ctx;
    boolean serviceBound;

    //Messenger for communicating with service.
    Messenger mService = null;

    //Handler of incoming messages from service.
    final Messenger mMessenger = new Messenger(new ActivityHandler());

    public PSDServiceWorker(Context context)
    {
        this.ctx = context;
    }


    /*
    we are starting and binding service to have it alive all the time. It won't die when
    this activity will die.
    */
    public void connectService()
    {
        ServiceConnection mConnection = new MyServiceConnection();
        Intent mServiceIntent = new Intent(ctx, PsdService.class);
        ctx.startService(mServiceIntent);
        ctx.bindService(mServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
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

    public void connectPsd(boolean persist)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("PERSIST", persist);
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
            Log.e(TAG, "ServiceWorker Service is not bound");
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
                case StateChanged:
                    receivedStateChanged(msg);
                    break;
                case PassSentSuccess:
                    onPassSentSuccess();
                    break;
                case Error:
                    receivedError(msg);
                    break;
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
            Log.d(TAG, "[ APP ] Service connected");
            mService = new Messenger(service);
            serviceBound = true;
            sendMessenger();
        }

        public void onServiceDisconnected(ComponentName name)
        {
            Log.d(TAG, "[ APP ] Service disconnected");
            mService = null;
            serviceBound = false;
        }
    }


    public abstract void onStateChanged(CurrentServiceState newState);

    public abstract void onPassSentSuccess();

    public abstract void onError(ErrorType err, String msg);
}
