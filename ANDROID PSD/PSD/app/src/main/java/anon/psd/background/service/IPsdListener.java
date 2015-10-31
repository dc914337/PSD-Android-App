package anon.psd.background.service;

import anon.psd.background.messages.BTError;
import anon.psd.background.states.ConnectionState;

/**
 * Created by Dmitry on 07.09.2015.
 */
public interface IPsdListener
{
    void onPsdError(BTError err);

    void onBTError(BTError err);

    void onConnectionStateChanged(ConnectionState newState);

    void onPassSentSuccess();
}
