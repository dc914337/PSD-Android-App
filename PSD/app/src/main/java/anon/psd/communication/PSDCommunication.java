package anon.psd.communication;

import anon.psd.background.messages.ErrorType;
import anon.psd.background.messages.ResponseMessageType;
import anon.psd.crypto.protocol.PsdProtocolV1;

import anon.psd.hardware.bluetooth.IBtObserver;
import anon.psd.hardware.bluetooth.PsdBluetoothProtocol;
import anon.psd.hardware.bluetooth.lowlevelV1.LowLevelMessageV1;
import anon.psd.models.PassItem;

/**
 * Created by Dmitry on 27/02/2016.
 */
public class PSDCommunication implements IBtObserver {
    private PSDState commState= PSDState.Disconnected;

    public PSDState getCommState()
    {
        return commState;
    }


    private void setCommState(PSDState newState)
    {
        if(newState!=commState)
        {
            commState=newState;
            observer.onCommStateChanged(commState);
        }
    }

    String psdMacAddress;
    int autoDisconnectSeconds=30;


    ICommunicationObserver observer;

    PsdBluetoothProtocol bt;
    PsdProtocolV1 protocolV1;
    boolean rememberedBtState;

    public PSDCommunication(ICommunicationObserver Observer, String PsdMacAddress, byte[] startBtKey, byte[] startHBtKey )
    {
        observer=Observer;
        psdMacAddress=PsdMacAddress;
        bt=new PsdBluetoothProtocol();
        protocolV1=new PsdProtocolV1(startBtKey,startHBtKey);
    }

    public void setAutoDisconnectSeconds(int seconds)
    {
autoDisconnectSeconds=seconds;
    }

    public void connectPSD()
    {
        rememberedBtState = bt.isBluetoothEnabled();
        bt.enableBluetooth();
        bt.registerObserver(this);
        bt.connectDevice(psdMacAddress);
    }


    public void disconnect()
    {
        bt.disconnectDevice();
        if (!rememberedBtState)
            bt.disableBluetooth();
        bt.removeObserver();
    }


    public void sendPassword(PassItem passItem)
    {
        byte[] encryptedMessage = protocolV1.generateSendPass(passItem.getPsdId(), passItem.getPasswordBytes());
        bt.sendPasswordBytes(encryptedMessage);
        setCommState(PSDState.Awaiting);
    }


    public void rollKeys()
    {
        if (protocolV1 != null)
        {
            protocolV1.rollKeys();
        }
    }


    //On receive message from PSD through bluetooth
    @Override
    public void onBtReceive(LowLevelMessageV1 message)
    {
        if (!protocolV1.checkResponse(message.message))
            return;
        //save new keys to db
        observer.updateKeysInDatabase(protocolV1.btKey, protocolV1.hBtKey);
        observer.onCommMessage(ResponseMessageType.PassSentSuccess, "Pass sent successfully");
        setCommState(PSDState.ReadyToSend);
    }

    @Override
    public void onBtError(ErrorType err, String errMessage) {

    }

    @Override
    public void onBtDisconnected() {
setCommState(PSDState.Disconnected);
    }

    @Override
    public void onBtConnected() {
        if(getCommState()== PSDState.Disconnected)
            setCommState(PSDState.ReadyToSend);
    }


}
