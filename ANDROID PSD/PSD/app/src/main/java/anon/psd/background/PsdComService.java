package anon.psd.background;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import anon.psd.notifications.ServiceNotification;

/**
 * Created by Dmitry on 01.08.2015.
 */
public class PsdComService extends IntentService
{
    public static final String SERVICE_NAME = "PsdComService";
    final String TAG = "PsdComService";


    final Messenger mMessenger = new Messenger(new ServiceHandler());


    ServiceNotification notification;


    public PsdComService()
    {
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        Log.d(TAG, "onHandleIntent");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind");
        return mMessenger.getBinder();
    }

    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }

    public boolean onUnbind(Intent intent)
    {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    class ServiceHandler extends Handler
    {
        Messenger mClient;

        @Override
        public void handleMessage(Message msg)
        {
            Log.d(TAG, "handleMessage");
            MessageTypes type = MessageTypes.fromInteger(msg.what);
            switch (type) {
                case ConnectService:
                    mClient = msg.replyTo;
                    break;

                default:
                    super.handleMessage(msg);
            }
        }


    }
}
