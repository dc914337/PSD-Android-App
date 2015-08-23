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
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
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
public class PsdConnectionService extends IntentService implements IBtObserver
{
    public static final String SERVICE_NAME = "PsdConnectionService";
    private final String TAG = "SERVICE";

    private CurrentServiceState serviceState = new CurrentServiceState();
    final Messenger mMessenger = new Messenger(new ServiceHandler());
    Messenger mClient;
    ServiceNotification notification;
    IBtObservable bt;
    PsdProtocolV1 protocolV1;
    boolean rememberedBtState;

    String psdMacAddress;
    FileRepository baseRepo;

    //On receive message from PSD through bluetooth
    @Override
    public void onReceive(LowLevelMessage message)
    {
        if (!protocolV1.checkResponse(message.message))
            return;
        //save new keys to db
        baseRepo.updateKeys(protocolV1.btKey, protocolV1.hBtKey);
        sendSuccess();
    }


    public void onProtocolStateChanged(ProtocolState newState)
    {
        if (serviceState.is(newState))
            return;

        serviceState.setProtocolState(newState);
        Log.d(TAG, String.format("Service [ STATE CHANGED ] %s", newState.toString()));
        sendServiceState();
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


    public PsdConnectionService()
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

    }

    class ServiceHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            RequestType type = RequestType.fromInteger(msg.what);
            Log.d(TAG, String.format("Service handleMessage %s", type.toString()));
            switch (type) {
                case ConnectService:
                    mClient = msg.replyTo;
                    sendServiceState();
                    break;
                case Init:
                    initService((Bundle) msg.obj);
                case ConnectPSD:
                    connect((Bundle) msg.obj);
                    break;
                case SendPass:
                    sendPassword((Bundle) msg.obj);
                    break;
                case DisconnectPSD:
                    disconnect();
                    break;
                case UpdateState:
                    sendServiceState();
                    break;
                case RollKeys:
                    rollKeys();
                    break;
                case Kill:
                    die();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void sendSuccess()
    {
        sendToClients(null, ResponseType.PassSentSuccess);
        onProtocolStateChanged(ProtocolState.ReadyToSend);
    }

    public void sendError(ErrorType err, String errMessage)
    {
        Bundle errBundle = new Bundle();
        errBundle.putInt("ERR_TYPE", err.getInt());
        errBundle.putString("ERR_MSG", errMessage);
        sendToClients(errBundle, ResponseType.Error);
    }

    private void sendToClients(Bundle bundle, ResponseType msgType)
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


    private void initService(Bundle bundle)
    {
        String dbPath = bundle.getString("DB_PATH");
        byte[] dbPass = bundle.getByteArray("DB_PASS");
        this.psdMacAddress = bundle.getString("PSD_MAC_ADDRESS");
        baseRepo = new FileRepository(dbPath);
        baseRepo.setDbPass(dbPass);
        if (!baseRepo.update()) {
            sendError(ErrorType.DBError, "Couldn't load database");
            return;
        }
        protocolV1 = new PsdProtocolV1(baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
        serviceState.setServiceState(ServiceState.Initialised); //here we don't have client messenger(mClient) yet. We will call sendState  when we will get mClient
    }

    private void connect(Bundle bundle)
    {
        boolean persist = bundle.getBoolean("PERSIST");
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
    }

    private void sendPassword(Bundle bundle)
    {
        short passId = bundle.getShort("PASS_ITEM_ID");
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


    private void die()
    {
        disconnect();
        stopSelf();
    }

    private void rollKeys()
    {
        if (protocolV1 != null)
            protocolV1.rollKeys();
    }


    private void sendServiceState()
    {
        Bundle bundle = new Bundle();
        bundle.putByteArray("SERVICE_STATE", serviceState.toByteArray());
        sendToClients(bundle, ResponseType.StateChanged);
    }


}
