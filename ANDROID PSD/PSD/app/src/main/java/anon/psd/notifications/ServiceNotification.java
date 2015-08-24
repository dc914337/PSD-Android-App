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

    private final static String TITLE = "PSD";

    public ServiceNotification(Context context)
    {
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

        //2-nd part
        notification.setLatestEventInfo(ctx, TITLE, text, pIntent);

        //notification will disappear after click
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //send notification
        nm.notify(1, notification);
    }


    public Notification getServiceWorkingNotification()
    {
        Intent clickIntent = new Intent(ctx, MainActivity.class);
        clickIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, clickIntent, 0);

        Notification note = new Notification.Builder(ctx).setContentTitle(TITLE).setContentText("PSD service is running").setSmallIcon(R.drawable.notification_template_icon_bg).setContentIntent(pi).build();
        note.flags |= Notification.FLAG_NO_CLEAR;
        return note;
    }

}
