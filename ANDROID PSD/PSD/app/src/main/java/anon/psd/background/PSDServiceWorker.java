package anon.psd.background;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import anon.psd.device.ConnectionState;

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


    public abstract void onStateChanged(ConnectionState newState);

    public abstract void onReceivedMessage(byte[] message);

    public PSDServiceWorker(Context context)
    {
        this.ctx = context;
    }

    public void connectService()
    {
        mConnection = new MyServiceConnection();
        Intent mServiceIntent = new Intent(ctx, PsdComService.class);
        //mServiceIntent.setData(Uri.parse("connect"));
        ctx.startService(mServiceIntent);
        ctx.bindService(mServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public ConnectionState getConnectionState()
    {
        return ConnectionState.Connected;
    }

    public void connectPsd()
    {
        Log.d(TAG, "ServiceWorker Connect PSD");
    }

    public void sendMessage(byte[] message)
    {
        Log.d(TAG, String.format("ServiceWorker Send message %s", message.length));
    }

    public void disconnectPsd()
    {
        Log.d(TAG, "ServiceWorker Disconnect PSD");
    }


    private class ActivityHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            MessageTypes type = MessageTypes.fromInteger(msg.what);
            switch (type) {
                case ConnectionState:

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
        Message msg = Message.obtain(null, MessageTypes.ConnectService.getInt());
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ServiceWorker Sent messenger to service");
    }

    private void sendCommandToService(MessageTypes msgType)
    {
        if (!serviceBound) {
            Log.e(TAG, "ServiceWorker Service is not bound");
            return;
        }

        Message msg = Message.obtain(null, msgType.getInt());
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, String.format("ServiceWorker Sent %s command", msgType.toString()));
    }


}
