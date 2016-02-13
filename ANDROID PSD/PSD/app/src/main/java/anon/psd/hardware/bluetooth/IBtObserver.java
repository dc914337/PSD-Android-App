package anon.psd.hardware.bluetooth;

import anon.psd.background.messages.ErrorType;
import anon.psd.device.state.ConnectionState;
import anon.psd.hardware.bluetooth.lowlevelV1.LowLevelMessageV1;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(LowLevelMessageV1 message);

    public void sendError(ErrorType err, String errMessage);

    void onConnectionStateChanged(ConnectionState newState);

}
