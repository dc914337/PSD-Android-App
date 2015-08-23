package anon.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.util.Date;

/**
 * Created by Dmitry on 23.08.2015.
 */
public class TestService extends Service
{
    private Date created;
    final Messenger mMessenger = new Messenger(new ServiceHandler());

    @Override
    public void onCreate()
    {
        super.onCreate();
        created = new Date();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d("DBG", "BIND to service that was created: " + created);
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Notification note = new Notification(R.drawable.notification_template_icon_bg,
                "Can you hear the music?",
                System.currentTimeMillis());
        Intent i = new Intent(this, MainActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi = PendingIntent.getActivity(this, 0,
                i, 0);

        note.setLatestEventInfo(this, "Fake Player",
                "Now Playing: \"Ummmm, Nothing\"",
                pi);
        note.flags |= Notification.FLAG_NO_CLEAR;

        startForeground(1337, note);
        return START_STICKY;
    }

    class ServiceHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            Log.d("DBG", "Received message. Service created: " + created);
        }
    }

}
