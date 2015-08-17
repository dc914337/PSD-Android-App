package anon.psd.background;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import anon.psd.crypto.protocol.PsdProtocolV1;
import anon.psd.device.ConnectionState;
import anon.psd.hardware.IBtObservable;
import anon.psd.hardware.IBtObserver;
import anon.psd.hardware.LowLevelMessage;
import anon.psd.hardware.PsdBluetoothCommunication;
import anon.psd.models.PassItem;
import anon.psd.notifications.ServiceNotification;
import anon.psd.storage.FileRepository;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 */
public class PsdComService extends IntentService implements IBtObserver
{
    public static final String SERVICE_NAME = "PsdComService";
    private final String TAG = "PsdComService";
    final Messenger mMessenger = new Messenger(new ServiceHandler());
    Messenger mClient;
    ServiceNotification notification;
    IBtObservable bt;
    PsdProtocolV1 protocolV1;


    String psdMacAddress;
    FileRepository baseRepo;


    public PsdComService()
    {
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "Service onCreate");
        bt = new PsdBluetoothCommunication();
        notification = new ServiceNotification(this);
    }

    private void initService(String dbPath, byte[] dbPass, String psdMacAddress)
    {
        baseRepo = new FileRepository(dbPath);
        baseRepo.setDbPass(dbPass);
        baseRepo.update();
        this.psdMacAddress = psdMacAddress;
        protocolV1 = new PsdProtocolV1(baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
    }


    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        Log.d(TAG, "Service onHandleIntent");
        Bundle extras = workIntent.getExtras();
        initService(extras.getString("DB_PATH"), extras.getByteArray("DB_PASS"), extras.getString("PSD_MAC_ADDRESS"));
    }


    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "Service onBind");
        return mMessenger.getBinder();
    }

    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
        Log.d(TAG, "Service onRebind");
    }

    public boolean onUnbind(Intent intent)
    {
        Log.d(TAG, "Service onUnbind");
        return super.onUnbind(intent);
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");
    }


    //On receive message from PSD through bluetooth
    @Override
    public void onReceive(LowLevelMessage message)
    {
        Log.i(TAG, message.type.toString());
        if (protocolV1.checkResponse(message.message)) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("success", true);
            sendToClients(bundle, MessageType.Result);
        }
    }

    @Override
    public void onStateChanged(ConnectionState newState)
    {
        Log.d(TAG, String.format("Service [ STATE CHANGED ] %s", newState.toString()));
        Bundle bundle = new Bundle();
        bundle.putInt("connection_state", newState.getInt());
        sendToClients(bundle, MessageType.ConnectionStateChanged);
    }


    private void sendToClients(Bundle bundle, MessageType msgType)
    {
        Message msg = Message.obtain(null, msgType.getInt(), bundle);
        try {
            mClient.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    class ServiceHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            MessageType type = MessageType.fromInteger(msg.what);
            Log.d(TAG, String.format("Service handleMessage %s", type.toString()));
            switch (type) {
                case ConnectService:
                    mClient = msg.replyTo;
                    break;
                case Connect:
                    connect();
                    break;
                case Disconnect:
                    disconnect();
                    break;
                case PasswordId:
                    Bundle bundle = (Bundle) msg.obj;
                    sendPassword(bundle.getShort("pass_item_id"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void connect()
    {
        if (bt == null)
            return;
        bt.enableBluetooth();
        bt.registerObserver(this);
        bt.connectDevice(psdMacAddress);
    }

    private void disconnect()
    {
        if (bt == null)
            return;
        bt.disconnectDevice();
        bt.disableBluetooth();
        bt.removeObserver();
    }

    private void sendPassword(short passId)
    {
        if (bt == null)
            return;
        PassItem passItem = baseRepo.getPassesBase().passwords.get(passId);
        //do encoding shit
        byte[] encryptedMessage = protocolV1.generateNextMessage(passId, passItem.getPasswordBytes());
        bt.sendPasswordBytes(encryptedMessage);
    }
}
