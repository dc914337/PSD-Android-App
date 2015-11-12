package anon.psd.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import anon.psd.background.service.PasswordForgetPolicyType;

/**
 * Created by Dmitry on 27.07.2015.
 */
public class PreferencesProvider
{
    private static final String USER_PASSWORD_KEY = "db_password";
    private static final String DB_PATH = "db_path";
    private static final String PSD_MAC = "psd_mac";
    private static final String FORGET_POLICY = "password_forget_policy";

    SharedPreferences sharedPrefs;

    public PreferencesProvider(Context context)
    {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setDbPass(byte[] value)
    {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        if (value == null) {
            editor.putString(USER_PASSWORD_KEY, null);
        } else {
            editor.putString(USER_PASSWORD_KEY, Base64.encodeToString(value, Base64.DEFAULT));
        }
        editor.commit();
    }

    public byte[] getDbPass()
    {
        String base64 = sharedPrefs.getString(USER_PASSWORD_KEY, null);
        if (base64 == null)
            return null;
        return Base64.decode(base64, Base64.DEFAULT);
    }


    public String getDbPath()
    {
        return sharedPrefs.getString(DB_PATH, null);
    }


    public String getPsdMacAddress()
    {
        return sharedPrefs.getString(PSD_MAC, null);
    }


    public PasswordForgetPolicyType getPasswordForgetPolicyType()
    {
        return PasswordForgetPolicyType.fromInteger(sharedPrefs.getInt(FORGET_POLICY, 0));
    }


}
