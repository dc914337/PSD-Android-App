package anon.psd.communication;

import anon.psd.background.messages.ErrorType;
import anon.psd.background.messages.ResponseMessageType;


/**
 * Created by Dmitry on 27/02/2016.
 */
public interface ICommunicationObserver {

    void onCommError(ErrorType err, String errMessage);
    void onCommMessage(ResponseMessageType type, String messageText);

    void onCommStateChanged(PSDState newState);

    void updateKeysInDatabase(byte[] BtKey, byte[] HBtKey);
}
