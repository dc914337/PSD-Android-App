package anon.psd.background;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.util.Date;

import anon.psd.device.ConnectionState;
import anon.psd.hardware.IBtObservable;
import anon.psd.hardware.IBtObserver;
import anon.psd.hardware.PsdBluetoothCommunication;
import anon.psd.models.DataBase;
import anon.psd.models.PassItem;
import anon.psd.notifications.ServiceNotification;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 */
public class PsdComService extends IntentService implements IBtObserver
{
    Date created;
    public static final String SERVICE_NAME = "PsdComService";
    private final String TAG = "PsdComService";
    final Messenger mMessenger = new Messenger(new ServiceHandler());
    ServiceNotification notification;
    IBtObservable bt;

    String PsdMacAddress;
    DataBase Base;


    public PsdComService()
    {
        super(SERVICE_NAME);
        created = new Date();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "Service onCreate");
        bt = new PsdBluetoothCommunication();
        notification = new ServiceNotification(this);
    }

    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        Log.d(TAG, "Service onHandleIntent " + created.toString());
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "Service onBind " + created.toString());
        return mMessenger.getBinder();
    }

    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
        Log.d(TAG, "Service onRebind " + created.toString());
    }

    public boolean onUnbind(Intent intent)
    {
        Log.d(TAG, "Service onUnbind " + created.toString());
        return super.onUnbind(intent);
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy " + created.toString());
    }


    //On receive message from PSD through bluetooth
    @Override
    public void onReceive(byte[] message)
    {

    }

    @Override
    public void onStateChanged(ConnectionState newState)
    {

    }


    class ServiceHandler extends Handler
    {
        Messenger mClient;

        @Override
        public void handleMessage(Message msg)
        {
            MessageType type = MessageType.fromInteger(msg.what);
            Log.d(TAG, String.format("Service handleMessage %s %s", type.toString(), created.toString()));
            switch (type) {
                case ConnectService:
                    mClient = msg.replyTo;
                    break;
                case Init:
                    init((ServiceInitObject) msg.obj);
                    break;
                case Connect:
                    connect();
                    break;
                case Disconnect:
                    disconnect();
                    break;
                case Password:
                    sendPassword((PassItem) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void init(ServiceInitObject initObject)
    {
        PsdMacAddress = initObject.PSDMacAddress;
        this.Base = initObject.Base;
    }

    private void connect()
    {
        if (bt == null)
            return;

        bt.enableBluetooth();
        bt.registerObserver(this);
        bt.connectDevice(PsdMacAddress);
    }

    private void disconnect()
    {
        if (bt == null)
            return;
        bt.disconnectDevice();
        bt.disableBluetooth();
        bt.disconnectDevice();
    }

    private void sendPassword(PassItem pass)
    {
        if (bt == null)
            return;
        bt.sendPasswordBytes(pass.getPasswordBytes());
    }
}
