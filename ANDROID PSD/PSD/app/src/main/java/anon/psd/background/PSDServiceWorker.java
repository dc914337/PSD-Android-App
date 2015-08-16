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

import anon.psd.device.ConnectionState;
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
    private ServiceConnection mConnection;

    public PSDServiceWorker(Context context)
    {
        this.ctx = context;
    }

    public void connectService(String dbPath, byte[] dbPass, String psdMacAddress)
    {
        mConnection = new MyServiceConnection();
        Intent mServiceIntent = new Intent(ctx, PsdComService.class);

        mServiceIntent.putExtra("DB_PATH", dbPath);
        mServiceIntent.putExtra("DB_PASS", dbPass);
        mServiceIntent.putExtra("PSD_MAC_ADDRESS", psdMacAddress);

        ctx.startService(mServiceIntent);
        ctx.bindService(mServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }


    public void connectPsd()
    {
        Log.d(TAG, "ServiceWorker Connect PSD");
        sendCommandToService(MessageType.Connect);
    }

    public void disconnectPsd()
    {
        Log.d(TAG, "ServiceWorker Disconnect PSD");
        sendCommandToService(MessageType.Disconnect);
    }

    public void sendPass(PassItem pass)
    {
        Log.d(TAG, String.format("ServiceWorker Sent pass id: %s", pass.Id));
        sendPassToService(pass);
    }


    private class ActivityHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            MessageType type = MessageType.fromInteger(msg.what);
            ConnectionState state = ConnectionState.fromInteger((int) ((Bundle) msg.obj).get("connection_state"));

            switch (type) {
                case ConnectionStateChanged:
                    onStateChanged(state);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
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

    /*
        we are starting and binding service to have it alive all the time. It won't die when
        this activity will die. We sending fake intent
    */


    private void sendMessenger()
    {
        Message msg = Message.obtain(null, MessageType.ConnectService.getInt());
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ServiceWorker Sent messenger to service");
    }

    private void sendCommandToService(MessageType msgType)
    {
        Message msg = Message.obtain(null, msgType.getInt());
        sendMessage(msg);
        Log.d(TAG, String.format("ServiceWorker Sent %s command", msgType.toString()));
    }

    private void sendPassToService(PassItem pass)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("pass_item_id", pass.Id);
        Message msg = Message.obtain(null, MessageType.PasswordId.getInt(), bundle);
        sendMessage(msg);
        Log.d(TAG, "ServiceWorker Sent [ PASS ] command");
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


    public abstract void onStateChanged(ConnectionState newState);

    public abstract void onReceivedResult(boolean res);

}
