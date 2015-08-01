package anon.psd.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import anon.psd.R;
import anon.psd.gui.activities.MainActivity;

/**
 * Created by Dmitry on 31.07.2015.
 */
public class ServiceNotification
{
    NotificationManager nm;
    private Context ctx;

    private final String TITLE;

    public ServiceNotification(Context context)
    {
        TITLE = context.getString(R.string.notification_title);
        ctx = context;
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sendNotification(String text)
    {
        Notification notification = new Notification(R.drawable.ic_launcher, text,
                System.currentTimeMillis());

        //set notification click opens main activity
        Intent intent = new Intent(ctx, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        // 2-я часть
        notification.setLatestEventInfo(ctx, TITLE, text, pIntent);

        //notification will disappear after click
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //send notification
        nm.notify(1, notification);
    }

}
