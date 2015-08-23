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
        Intent mServiceIntent = new Intent(ctx, PsdConnectionService.class);
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

    public void connectPsd()
    {
        sendCommandToService(RequestType.ConnectPSD);
    }

    public void disconnectPsd()
    {
        sendCommandToService(RequestType.DisconnectPSD);
    }

    public void sendPass(PassItem pass)
    {
        Bundle bundle = new Bundle();
        bundle.putShort("pass_item_id", pass.id);
        Message msg = Message.obtain(null, RequestType.SendPass.getInt(), bundle);
        sendMessage(msg);
    }


    public void updateState()
    {
        sendCommandToService(RequestType.UpdateState);
    }

    private class ActivityHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            RequestType type = RequestType.fromInteger(msg.what);
            switch (type) {
                case ConnectionStateChanged:
                    receivedStateChanged(msg);
                    break;
                case PassSendResult:
                    receivedResult(msg);
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
        String message = bundle.getString("err_msg");
        ErrorType type = ErrorType.fromInteger(bundle.getInt("err_type"));
        onReceivedError(type, message);
    }

    private void receivedStateChanged(Message msg)
    {
        CurrentServiceState state = CurrentServiceState.fromByteArray(
                (byte[]) ((Bundle) msg.obj).get("service_state"));
        onStateChanged(state);
    }

    private void receivedResult(Message msg)
    {
        boolean success = ((Bundle) msg.obj).getBoolean("success");
        onReceivedResult(success);
    }


    private class MyServiceConnection implements ServiceConnection
    {
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.d(TAG, "ServiceWorker onServiceConnected");
            mService = new Messenger(service);
            serviceBound = true;
            sendMessenger();
        }

        public void onServiceDisconnected(ComponentName name)
        {
            Log.d(TAG, "ServiceWorker onServiceDisconnected");
            mService = null;
            serviceBound = false;
        }
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


    public abstract void onStateChanged(CurrentServiceState newState);

    public abstract void onReceivedResult(boolean res);

    public abstract void onReceivedError(ErrorType err, String msg);
}
