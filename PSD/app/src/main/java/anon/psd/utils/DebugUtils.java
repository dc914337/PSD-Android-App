package anon.psd.utils;

import android.util.Log;

/**
 * Created by Dmitry on 24.08.2015.
 */
public class DebugUtils
{
    public static void Log(Object caller, String message, Object... args)
    {
        Log.d(caller.getClass().getSimpleName(), String.format(message, args));
    }

    public static void LogErr(Object caller, String message, Object... args)
    {
        Log.e(caller.getClass().getSimpleName(), String.format(message, args));
    }
}
