package anon.psd.gui.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

public class PathPreference extends Preference
{
    String value;


    public PathPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue) {
            // Restore existing state
            setValue(this.getPersistedString((String) defaultValue));
        } else {
            // Set default state from the XML attribute
            setValue((String) defaultValue);
        }
    }

    private void setValue(String value)
    {
        persistString(value);
        setSummary(value);
    }


    @Override
    public void onClick()
    {
        //call open file dialog
        //set value to opened file
        value = "/home/sdcard0/PSD/phonePsdBase.psd";
        setValue(value);


    }
}
