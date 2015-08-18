package anon.psd.hardware;

import anon.psd.device.ServiceState;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onReceive(LowLevelMessage message);
    void onStateChanged(ServiceState newState);
}
