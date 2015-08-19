package anon.psd.hardware;

import anon.psd.device.state.ConnectionState;
import anon.psd.device.state.ProtocolState;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(LowLevelMessage message);

    void onConnectionStateChanged(ConnectionState newState);

    void onProtocolStateChanged(ProtocolState newState);
}
