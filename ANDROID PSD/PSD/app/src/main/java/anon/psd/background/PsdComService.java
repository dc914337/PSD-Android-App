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
import anon.psd.device.ServiceState;
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
    private ServiceState serviceState = ServiceState.NotInitialised;

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
        bt = new PsdBluetoothCommunication();
        notification = new ServiceNotification(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mMessenger.getBinder();
    }

    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        Bundle extras = workIntent.getExtras();
        initService(extras.getString("DB_PATH"), extras.getByteArray("DB_PASS"), extras.getString("PSD_MAC_ADDRESS"));
    }

    private void initService(String dbPath, byte[] dbPass, String psdMacAddress)
    {
        this.psdMacAddress = psdMacAddress;
        baseRepo = new FileRepository(dbPath);
        baseRepo.setDbPass(dbPass);
        if (!baseRepo.update()) {
            onStateChanged(ServiceState.NotInitialised);
            return;
        }
        protocolV1 = new PsdProtocolV1(baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
        onStateChanged(ServiceState.NotConnected); //here we don't have client messenger(mClient) yet. We will call sendState  when we will get mClient
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
                    //init sending of current state. Not changing it
                    Bundle bundle = new Bundle();
                    bundle.putInt("service_state", serviceState.getInt());
                    sendToClients(bundle, MessageType.ConnectionStateChanged);
                    break;
                case ConnectPSD:
                    connect();
                    break;
                case SendPass:
                    sendPassword((Bundle) msg.obj);
                    break;
                case DisconnectPSD:
                    disconnect();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    //On receive message from PSD through bluetooth
    @Override
    public void onReceive(LowLevelMessage message)
    {
        if (protocolV1.checkResponse(message.message)) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("success", true);
            sendToClients(bundle, MessageType.PassSendResult);
            onStateChanged(ServiceState.ReadyToSend);
        }
    }

    @Override
    public void onStateChanged(ServiceState newState)
    {
        if (newState == serviceState)
            return;
        serviceState = newState;
        Log.d(TAG, String.format("Service [ STATE CHANGED ] %s", newState.toString()));
        Bundle bundle = new Bundle();
        bundle.putInt("service_state", newState.getInt());
        sendToClients(bundle, MessageType.ConnectionStateChanged);
    }


    private void sendToClients(Bundle bundle, MessageType msgType)
    {
        if (mClient == null)
            return;

        Message msg = Message.obtain(null, msgType.getInt(), bundle);
        try {
            mClient.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    private void connect()
    {
        if (serviceState != ServiceState.NotConnected)
            return;
        bt.enableBluetooth();
        bt.registerObserver(this);
        bt.connectDevice(psdMacAddress);
    }

    private void disconnect()
    {
        if (serviceState == ServiceState.NotInitialised ||
                serviceState == ServiceState.NotConnected)
            return;
        bt.disconnectDevice();
        bt.disableBluetooth();
        bt.removeObserver();
    }

    private void sendPassword(Bundle bundle)
    {
        short passId = bundle.getShort("pass_item_id");
        //We can send password only if connected or low signal
        if (!(serviceState == ServiceState.ReadyToSend ||
                serviceState == ServiceState.LowSignal))
            return;

        PassItem passItem = baseRepo.getPassesBase().passwords.get(passId);
        byte[] encryptedMessage = protocolV1.generateNextMessage(passId, passItem.getPasswordBytes());
        bt.sendPasswordBytes(encryptedMessage);
        onStateChanged(ServiceState.WaitingResponse);
    }
}
