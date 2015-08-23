package anon.psd.background;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Date;

import anon.psd.R;
import anon.psd.crypto.protocol.PsdProtocolV1;
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
import anon.psd.gui.activities.MainActivity;
import anon.psd.hardware.bluetooth.IBtObservable;
import anon.psd.hardware.bluetooth.IBtObserver;
import anon.psd.hardware.bluetooth.PsdBluetoothCommunication;
import anon.psd.hardware.bluetooth.lowlevel.LowLevelMessage;
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

    private CurrentServiceState serviceState = new CurrentServiceState();
    final Messenger mMessenger = new Messenger(new ServiceHandler());
    Messenger mClient;
    ServiceNotification notification;
    IBtObservable bt;
    PsdProtocolV1 protocolV1;
    boolean rememberedBtState;

    Date created = null;


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
        created = new Date();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Notification note = new Notification(R.drawable.ic_little_green,
                "Can you hear the music?",
                System.currentTimeMillis());
        Intent i = new Intent(this, MainActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi = PendingIntent.getActivity(this, 0,
                i, 0);

        note.setLatestEventInfo(this, "Fake Player",
                "Now Playing: \"Ummmm, Nothing\"",
                pi);
        note.flags |= Notification.FLAG_NO_CLEAR;

        startForeground(1337, note);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d("ALIVE", created.toString());
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
            sendError(ErrorType.DBError, "Couldn't load database");
            return;
        }
        protocolV1 = new PsdProtocolV1(baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
        serviceState.setServiceState(ServiceState.Initialised); //here we don't have client messenger(mClient) yet. We will call sendState  when we will get mClient
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
                    sendServiceState();
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
        if (!protocolV1.checkResponse(message.message))
            return;

        //save new keys to db
        baseRepo.updateKeys(protocolV1.btKey, protocolV1.hBtKey);

        //send success to application
        Bundle bundle = new Bundle();
        bundle.putBoolean("success", true);
        sendToClients(bundle, MessageType.PassSendResult);
        onProtocolStateChanged(ProtocolState.ReadyToSend);
    }

    @Override
    public void onConnectionStateChanged(ConnectionState newState)
    {
        if (serviceState.is(newState))
            return;

        serviceState.setConnectionState(newState);
        Log.d(TAG, String.format("Service [ STATE CHANGED ] %s", newState.toString()));
        sendServiceState();
    }

    public void onProtocolStateChanged(ProtocolState newState)
    {
        if (serviceState.is(newState))
            return;

        serviceState.setProtocolState(newState);
        Log.d(TAG, String.format("Service [ STATE CHANGED ] %s", newState.toString()));
        sendServiceState();
    }

    private void sendServiceState()
    {
        Bundle bundle = new Bundle();
        bundle.putByteArray("service_state", serviceState.toByteArray());
        sendToClients(bundle, MessageType.ConnectionStateChanged);
    }

    @Override
    public void sendError(ErrorType err, String errMessage)
    {
        Bundle errBundle = new Bundle();
        errBundle.putInt("err_type", err.getInt());
        errBundle.putString("err_msg", errMessage);
        sendToClients(errBundle, MessageType.Error);
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
        if (serviceState.is(ConnectionState.Connected)) {
            sendError(ErrorType.WrongState, "PSD is already connected");
            return;
        } else if (serviceState.is(ServiceState.NotInitialised)) {
            sendError(ErrorType.WrongState, "PSD is not initialised. Errors while initialising");
            return;
        }
        rememberedBtState = bt.isBluetoothEnabled();

        bt.enableBluetooth();
        bt.registerObserver(this);
        bt.connectDevice(psdMacAddress);
    }

    private void disconnect()
    {
        if (serviceState.is(ConnectionState.Disconnected)) {
            sendError(ErrorType.WrongState, "PSD is not connected");
            return;
        } else if (serviceState.is(ServiceState.NotInitialised)) {
            sendError(ErrorType.WrongState, "Service is not initialised");
            return;
        }
        bt.disconnectDevice();
        if (!rememberedBtState)
            bt.disableBluetooth();
        bt.removeObserver();
        stopForeground(true);
    }

    private void sendPassword(Bundle bundle)
    {
        short passId = bundle.getShort("pass_item_id");
        if (serviceState.is(ConnectionState.Disconnected)) {
            sendError(ErrorType.WrongState, "PSD is not connected");
            return;
        }

        if (serviceState.is(ProtocolState.WaitingResponse)) {
            sendError(ErrorType.WrongState, "We are still waiting for response from PSD");
            return;
        }

        PassItem passItem = baseRepo.getPassesBase().passwords.get(passId);
        byte[] encryptedMessage = protocolV1.generateNextMessage(passId, passItem.getPasswordBytes());
        bt.sendPasswordBytes(encryptedMessage);
        onProtocolStateChanged(ProtocolState.WaitingResponse);
    }
}
