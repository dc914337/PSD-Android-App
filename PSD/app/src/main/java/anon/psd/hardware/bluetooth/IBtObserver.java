package anon.psd.hardware.bluetooth;

import anon.psd.background.messages.ErrorType;
import anon.psd.hardware.bluetooth.lowlevelV1.LowLevelMessageV1;

/**
 * Created by Dmitry on 12.08.2015.
 */
public interface IBtObserver
{
    void onBtReceive(LowLevelMessageV1 message);

    void onBtError(ErrorType err, String errMessage);

    void onBtDisconnected();

    void onBtConnected();

}
