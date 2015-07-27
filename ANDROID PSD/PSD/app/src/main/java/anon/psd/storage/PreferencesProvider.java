package anon.psd.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Dmitry on 27.07.2015.
 */
public class PreferencesProvider
{
    private final String USER_PASSWORD_KEY = "user_password";
    SharedPreferences sharedPrefs;

    public PreferencesProvider(Context context)
    {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public String getUserPass()
    {
        return sharedPrefs.getString(USER_PASSWORD_KEY, null);
    }

    public void setUserPass(String value)
    {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(USER_PASSWORD_KEY, value);
        editor.commit();
    }


    public String getDbPath()
    {
        return sharedPrefs.getString("db_path", null);
    }

}
