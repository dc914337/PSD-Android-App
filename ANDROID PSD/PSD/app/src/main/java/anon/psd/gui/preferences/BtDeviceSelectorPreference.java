package anon.psd.gui.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import anon.psd.gui.activities.SettingsActivity;

/**
 * Created by Dmitry on 13.08.2015.
 */
public class BtDeviceSelectorPreference extends Preference
{
    Context context;

    public BtDeviceSelectorPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue) {
            // Restore existing state
            setValue(this.getPersistedString(
                    (String) defaultValue));
        } else {
            // Set default state from the XML attribute
            setValue((String)defaultValue);
        }
    }

    public void setValue(String value)
    {
        persistString(value);
        setSummary(value);
    }

    @Override
    public void onClick()
    {
        SettingsActivity activity = (SettingsActivity) context;
        activity.getMac(this);
    }
}
