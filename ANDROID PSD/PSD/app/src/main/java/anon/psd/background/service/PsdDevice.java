package anon.psd.background.service;

import anon.psd.crypto.protocol.PsdProtocolV1;
import anon.psd.device.ConnectionState;
import anon.psd.hardware.bluetooth.IBtObservable;
import anon.psd.hardware.bluetooth.IBtObserver;
import anon.psd.hardware.bluetooth.PsdBluetoothCommunication;
import anon.psd.hardware.bluetooth.lowlevel.BTError;
import anon.psd.hardware.bluetooth.lowlevel.LowLevelMessage;
import anon.psd.models.PassItem;
import anon.psd.storage.FileRepository;

import static anon.psd.utils.DebugUtils.Log;

/**
 * Created by Dmitry on 07.09.2015.
 */
public class PsdDevice implements IBtObserver
{
    FileRepository baseRepo;
    PsdProtocolV1 protocolV1;
    PsdBluetoothCommunication bt;

    public ConnectionState state = ConnectionState.NotAvailable;
    String psdMacAddress;

    IPsdListener listener;

    public PsdDevice(IPsdListener listener, FileRepository baseRepo, String psdMacAddress)
    {
        this.listener = listener;
        this.psdMacAddress = psdMacAddress;
        this.baseRepo = baseRepo;

        protocolV1 = new PsdProtocolV1(baseRepo.getPassesBase().btKey, baseRepo.getPassesBase().hBTKey);
    }

    public enum ConnectResult
    {
        AlreadyConnected,
        Connected,
        CantConnect
    }

    public ConnectResult connect(boolean persist)
    {
        Log(this, "[ PSD ] Connect PSD");
        if (state == ConnectionState.Connected)
        {
            return ConnectResult.AlreadyConnected;
        }

        bt = new PsdBluetoothCommunication();
        bt.enableBluetooth();
        bt.registerObserver(this);
        IBtObservable.BtConnectResult conRes = bt.connectDevice(psdMacAddress);
        if (conRes == IBtObservable.BtConnectResult.Connected)
            return ConnectResult.Connected;
        else
            return ConnectResult.CantConnect;
    }


    public enum DisconnectResult
    {
        AlreadyDisconnected,
        Disconnected
    }

    public DisconnectResult disconnect()
    {
        Log(this, "[ PSD ] Disconnect PSD");
        if (state == ConnectionState.Disconnected)
        {
            listener.onPsdError(BTError.NotConnected);
            return DisconnectResult.AlreadyDisconnected;
        }

        bt.disconnectDevice();
        bt.removeObserver();
        return DisconnectResult.Disconnected;
    }

    public enum SendPassResult
    {
        NotConnected,
        AwaitingPreviousResponse,
        Seegegeg
    }

    public SendPassResult sendPassword(short passId)
    {
        Log(this, "[ PSD ] Send pass to PSD. Pass id: %s", passId);
        if (state == ConnectionState.Disconnected)
        {
            listener.onPsdError(BTError.NotConnected);
            return SendPassResult.NotConnected;
        }

        if (protocolV1.state == ProtocolState.WaitingResponse)
        {
            listener.onPsdError(BTError.WaitingResponse);
            return SendPassResult.AwaitingPreviousResponse;
        }

        PassItem passItem = baseRepo.getPassesBase().passwords.get(passId);
        byte[] encryptedMessage = protocolV1.generateNextMessage(passId, passItem.getPasswordBytes());
        bt.sendPasswordBytes(encryptedMessage);
        return
    }


    public void rollKeys()
    {
        Log(this, "[ PSD ] Roll keys");
        if (protocolV1 != null)
            protocolV1.rollKeys();
    }


    //On receive message from PSD through bluetooth


    @Override
    public void onBtConnectionStateChanged(ConnectionState newState)
    {

    }


    @Override
    public void onReceive(LowLevelMessage message)
    {
        if (!protocolV1.checkResponse(message.message))
            return;
        //save new keys to db
        baseRepo.updateKeys(protocolV1.btKey, protocolV1.hBtKey);
    }

    @Override
    public void onConnectionStateChanged(anon.psd.device.state.ConnectionState newState)
    {
        state = newState;
        listener.onConnectionStateChanged(newState);
    }
}


