package anon.psd.background.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import anon.psd.background.messages.ErrorType;
import anon.psd.background.messages.RequestType;
import anon.psd.background.messages.ResponseMessageType;
import anon.psd.background.messages.ResponseType;
import anon.psd.crypto.protocol.PsdProtocolV1;
import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.CurrentServiceState;
import anon.psd.device.state.ProtocolState;
import anon.psd.device.state.ServiceState;
import anon.psd.hardware.bluetooth.IBtObserver;
import anon.psd.hardware.bluetooth.PsdBluetoothCommunication;
import anon.psd.hardware.bluetooth.lowlevelV1.LowLevelMessageV1;
import anon.psd.models.PassItem;
import anon.psd.notifications.ServiceNotification;
import anon.psd.serializers.Serializer;
import anon.psd.storage.FileRepository;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 */
public class PsdService extends IntentService implements IBtObserver
{
    public static final String SERVICE_NAME = "PsdService";

    private CurrentServiceState serviceState = new CurrentServiceState();
    final Messenger mMessenger = new Messenger(new ServiceHandler());
    Messenger mClient;
    ServiceNotification notification;
    PsdBluetoothCommunication bt;
    PsdProtocolV1 protocolV1;
    boolean rememberedBtState;
    PasswordForgetPolicyType forgetPolicy = PasswordForgetPolicyType.WhileServiceAlive;
    int autoDisconnectSeconds;
    boolean disconnectAfterSend =false;


    String psdMacAddress;
    FileRepository baseRepo;

    //On receive message from PSD through bluetooth
    @Override
    public void onReceive(LowLevelMessageV1 message)
    {
        if (!protocolV1.checkResponse(message.message))
            return;
        //save new keys to db
        baseRepo.updateKeys(protocolV1.btKey, protocolV1.hBtKey);
        sendMessage(ResponseMessageType.PassSentSuccess, "Pass sent successfully");
        onProtocolStateChanged(ProtocolState.ReadyToSend);
        if(disconnectAfterSend)
        {
            disconnect();
            disconnectAfterSend=false;
        }
    }

    public void onProtocolStateChanged(ProtocolState newState)
    {
        if (serviceState.is(newState))
            return;

        serviceState.setProtocolState(newState);
        Log(this, "[ SERVICE ] State changed: %s", newState.toString());
        sendServiceState();
    }


    @Override
    public void onConnectionStateChanged(ConnectionState newState)
    {
        /*if (serviceState.is(newState))
            return;*/

        serviceState.setConnectionState(newState);
        Log(this, "[ SERVICE ] State changed: %s", newState.toString());
        sendServiceState();
    }


    public PsdService()
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
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startForeground(1337, notification.getServiceWorkingNotification());
        return START_STICKY;
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
            Log(this, "[ SERVICE ] Handle message: %s", type.toString());
            switch (type) {
                case ConnectService:
                    connectService(msg.replyTo);
                    break;
                case Init:
                    initService((Bundle) msg.obj);
                    break;
                case GetPassesInfo:
                    sendPassesInfo();
                    break;
                case ConnectPSD:
                    connectPSD((Bundle) msg.obj);
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


    private void connectService(Messenger messenger)
    {
        Log(this, "[ RECEIVED ] Connect service");
        mClient = messenger;
        sendServiceState();
    }

    private void sendMessage(ResponseMessageType type, String messageText)
    {
        Bundle msgBundle = new Bundle();
        msgBundle.putInt("MSG_TYPE", type.getInt());
        msgBundle.putString("MSG_MSG", messageText);
        sendToClients(msgBundle, ResponseType.Message);
    }

    public void sendError(ErrorType err, String errMessage)
    {
        Bundle errBundle = new Bundle();
        errBundle.putInt("ERR_TYPE", err.getInt());
        errBundle.putString("ERR_MSG", errMessage);
        sendToClients(errBundle, ResponseType.Error);
    }

    private void sendServiceState()
    {
        Log(this, "[ SERVICE ]Send service state");
        Bundle bundle = new Bundle();
        bundle.putByteArray("SERVICE_STATE", serviceState.toByteArray());
        sendToClients(bundle, ResponseType.State);
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
        Log(this, "[ RECEIVED ] Init service");
        String dbPath = bundle.getString("DB_PATH");
        byte[] dbPass = bundle.getByteArray("DB_PASS");
        this.psdMacAddress = bundle.getString("PSD_MAC_ADDRESS");
        autoDisconnectSeconds = bundle.getInt("AUTO_DISCONNECT_SECONDS");
        forgetPolicy = PasswordForgetPolicyType.fromInteger(bundle.getInt("FORGET_POLICY"));
        baseRepo = new FileRepository(dbPath);
        baseRepo.setDbPass(dbPass);
        if (!baseRepo.update()) {
            sendError(ErrorType.DBError, "Couldn't access database \n(user pass or database path are incorrect)");
            return;
        }
        protocolV1 = new PsdProtocolV1(baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
        serviceState.setServiceState(ServiceState.Initialised);
        sendServiceState();
    }


    private void sendPassesInfo()
    {
        Log(this, "[ RECEIVED ] Send passes info");
        String serialized = Serializer.serializePasswordList(baseRepo.getPassesBase().passwords.getCopyWithoutPasswords());
        Bundle bundle = new Bundle();
        bundle.putString("PASSES_INFO", serialized);
        sendToClients(bundle, ResponseType.PassesInfo);
    }


    private void connectPSD(Bundle bundle)
    {
        Log(this, "[ RECEIVED ] Connect PSD");
        connectPSD();
    }


    private void connectPSD()
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
        Log(this, "[ RECEIVED ] Disconnect PSD");
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
        disconnectAfterSend = bundle.getBoolean("DISCONNECT_AFTER_SEND");
        Log(this, "[ RECEIVED ] Send pass to PSD. Pass id: %s", passId);
        if (serviceState.is(ConnectionState.Disconnected)) {
            connectPSD();
        }

        if (serviceState.is(ProtocolState.WaitingResponse)) {
            sendError(ErrorType.WrongState, "We are still waiting for response from PSD");
            return;
        }

        PassItem passItem = baseRepo.getPassesBase().passwords.get(passId);
        byte[] encryptedMessage = protocolV1.generateSendPass(passId, passItem.getPasswordBytes());
        bt.sendPasswordBytes(encryptedMessage);
        onProtocolStateChanged(ProtocolState.WaitingResponse);
    }


    private void die()
    {
        Log(this, "[ RECEIVED ] Service die");
        disconnect();
        stopForeground(true);
        stopSelf();
    }

    private void rollKeys()
    {
        Log(this, "[ RECEIVED ] Roll keys");
        if (protocolV1 != null)
        {
            protocolV1.rollKeys();
        }
    }





}
