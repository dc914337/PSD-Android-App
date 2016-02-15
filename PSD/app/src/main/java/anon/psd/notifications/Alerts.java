package anon.psd.notifications;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dmitry on 27.07.2015.
 */
public class Alerts
{
    private static final int SHOW_LENGTH = Toast.LENGTH_SHORT;

    public static void showMessage(Context context, java.lang.String format, java.lang.Object... args)
    {
        Toast.makeText(context, String.format(format, args), SHOW_LENGTH).show();
    }

}
