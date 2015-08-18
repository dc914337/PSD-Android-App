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

import anon.psd.device.ServiceState;
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
        sendCommandToService(MessageType.ConnectPSD);
    }

    public void disconnectPsd()
    {
        Log.d(TAG, "ServiceWorker Disconnect PSD");
        sendCommandToService(MessageType.DisconnectPSD);
    }

    public void sendPass(PassItem pass)
    {
        Log.d(TAG, String.format("ServiceWorker Sent pass id: %s", pass.id));
        sendPassToService(pass);
    }


    private class ActivityHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            MessageType type = MessageType.fromInteger(msg.what);
            switch (type) {
                case ConnectionStateChanged:
                    receivedStateChanged(msg);
                    break;
                case PassSendResult:
                    receivedResult(msg);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    private void receivedStateChanged(Message msg)
    {
        ServiceState state = ServiceState.fromInteger((int) ((Bundle) msg.obj).get("service_state"));
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
    }

    private void sendCommandToService(MessageType msgType)
    {
        Message msg = Message.obtain(null, msgType.getInt());
        sendMessage(msg);
    }

    private void sendPassToService(PassItem pass)
    {
        Bundle bundle = new Bundle();
        bundle.putShort("pass_item_id", pass.id);
        Message msg = Message.obtain(null, MessageType.SendPass.getInt(), bundle);
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


    public abstract void onStateChanged(ServiceState newState);

    public abstract void onReceivedResult(boolean res);

}
