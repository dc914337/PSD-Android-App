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
import anon.psd.communication.ICommunicationObserver;
import anon.psd.communication.PSDCommunication;
import anon.psd.communication.PSDState;
import anon.psd.models.PassItem;
import anon.psd.notifications.ServiceNotification;
import anon.psd.serializers.Serializer;
import anon.psd.storage.FileRepository;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 01.08.2015.
 * Happy birthday me, yay!
 */
public class PsdService extends IntentService implements ICommunicationObserver
{
    public static final String SERVICE_NAME = "PsdService";
    final Messenger mMessenger = new Messenger(new ServiceHandler());
    Messenger mClient;
    ServiceNotification notification;
    PasswordForgetPolicyType forgetPolicy = PasswordForgetPolicyType.WhileServiceAlive;
    FileRepository baseRepo;
    PSDCommunication psd;

    boolean initialized=false;


    public void onCommMessage(ResponseMessageType type, String messageText)
    {
        Bundle msgBundle = new Bundle();
        msgBundle.putInt("MSG_TYPE", type.getInt());
        msgBundle.putString("MSG_MSG", messageText);
        sendToClients(msgBundle, ResponseType.Message);
    }

    @Override
    public void onCommStateChanged(PSDState newState) {
        Log(this, "[ SERVICE ] State changed: %s", newState.toString());
        sendCommState();
    }

    @Override
    public void updateKeysInDatabase(byte[] BtKey, byte[] HBtKey) {
        baseRepo.updateKeys(BtKey, HBtKey);
    }

    public void onCommError(ErrorType err, String errMessage)
    {
        Bundle errBundle = new Bundle();
        errBundle.putInt("ERR_TYPE", err.getInt());
        errBundle.putString("ERR_MSG", errMessage);
        sendToClients(errBundle, ResponseType.Error);
    }


    public PsdService()
    {
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
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
                    sendCommState();
                    break;
                case RollKeys:
                    psd.rollKeys();
                    break;
                case Kill:
                    die();
                    break;
                case IsServiceInitialized:
                    sendInitializedState();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void sendPassword(Bundle bundle) {
        short passId = bundle.getShort("PASS_ITEM_ID");

        if (psd.getCommState()== PSDState.Awaiting) {
            onCommError(ErrorType.WrongState, "We are still waiting for response from PSD");
            return;
        }

        if (psd.getCommState()== PSDState.Disconnected)
           if(!connectPSD()) {
               onCommError(ErrorType.CantConnectDevice, "Can't connect PSD. Is it plugged?");
               return;
           }

        PassItem passItem = baseRepo.getPassesBase().rootGroup.getAllSubPasses().get(passId);
        psd.sendPassword(passItem);
    }



    private void connectPSD(Bundle bundle) {
        int autoDisconnectSeconds = bundle.getInt("AUTO_DISCONNECT_SECS");
        psd.setAutoDisconnectSeconds(autoDisconnectSeconds);
        connectPSD();
    }


    private boolean connectPSD()
    {
        if (psd==null) {
            onCommError(ErrorType.WrongState, "PSD is not initialised. Errors while initialising");
            return false;
        }
        else if (psd.getCommState()!= PSDState.Disconnected) {
            psd.resetAutoDisconnect();
            return true;
        }
        return psd.connectPSD();
    }







    private void disconnect() {
        if (psd.getCommState()== PSDState.Disconnected) {
            onCommError(ErrorType.WrongState, "PSD is not connected");
            return;
        }
        psd.disconnect();
    }



    private void connectService(Messenger messenger) {
        Log(this, "[ RECEIVED ] Connect service");
        mClient = messenger;
        sendInitializedState();
    }

    private void sendInitializedState()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("SERVICE_INITIALIZED", initialized);
        sendToClients(bundle, ResponseType.Initialized);
    }


    private void sendCommState()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("COMM_STATE", psd.getCommState().getInt());
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
        String dbPath = bundle.getString("DB_PATH");
        byte[] dbPass = bundle.getByteArray("DB_PASS");
        String psdMacAddress = bundle.getString("PSD_MAC_ADDRESS");
        forgetPolicy = PasswordForgetPolicyType.fromInteger(bundle.getInt("FORGET_POLICY"));
        baseRepo = new FileRepository(dbPath);
        baseRepo.setDbPass(dbPass);
        if (!baseRepo.update()) {
            onCommError(ErrorType.DBError, "Couldn't access database \n(user pass or database path are incorrect)");
            return;
        }
        psd=new PSDCommunication(this,psdMacAddress,baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
        initialized=true;
        sendInitializedState();
    }


    private void sendPassesInfo()
    {
        String serialized = Serializer.serializePasswordList(baseRepo.getPassesBase().rootGroup.getAllSubPasses().getCopyWithoutPasswords());
        Bundle bundle = new Bundle();
        bundle.putString("PASSES_INFO", serialized);
        sendToClients(bundle, ResponseType.PassesInfo);
    }




    private void die()
    {
        psd.disconnect();
        stopForeground(true);
        stopSelf();
    }


}
