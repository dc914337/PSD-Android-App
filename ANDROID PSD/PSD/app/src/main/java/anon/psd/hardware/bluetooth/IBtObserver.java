package anon.psd.hardware.bluetooth;

import anon.psd.background.messages.ErrorType;
import anon.psd.device.state.ConnectionState;
import anon.psd.hardware.bluetooth.lowlevel.LowLevelMessage;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(LowLevelMessage message);

    void onConnectionStateChanged(ConnectionState newState);

}
