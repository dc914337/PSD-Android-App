package anon.psd.communication;

import android.database.Observable;


/**
 * Created by Dmitry on 27/02/2016.
 */
public interface IPSDProtocol {
    //connect
    void connect();

    //check connection
    boolean isConnected();

    void sendPing();

    void sendPass();

    void sendAddPass();

    void sendRemovePass();

    void disconnect();

}
