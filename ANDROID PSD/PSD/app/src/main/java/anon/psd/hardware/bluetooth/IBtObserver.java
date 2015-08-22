package anon.psd.hardware.bluetooth;

import anon.psd.background.ErrorType;
import anon.psd.device.state.ConnectionState;
import anon.psd.hardware.bluetooth.lowlevel.LowLevelMessage;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(LowLevelMessage message);

    public void sendError(ErrorType err, String errMessage);

    void onConnectionStateChanged(ConnectionState newState);

}
