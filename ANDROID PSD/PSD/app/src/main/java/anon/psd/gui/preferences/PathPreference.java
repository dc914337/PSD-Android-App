package anon.psd.gui.preferences;

import android.content.Context;
import android.os.Environment;
import android.preference.Preference;
import android.util.AttributeSet;

import java.io.File;

public class PathPreference extends Preference
{
    File value;


    public PathPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue) {
            // Restore existing state
            setValue(new File(
                    this.getPersistedString(
                            (String) defaultValue)));
        } else {
            // Set default state from the XML attribute
            setValue((File) defaultValue);
        }
    }

    private void setValue(File value)
    {
        persistString(value.getAbsolutePath());
        setSummary(value.getAbsolutePath());
    }


    @Override
    public void onClick()
    {
        //call open file dialog
        //set value to opened file
        value = new File(Environment.getExternalStorageDirectory(), "home/psd/phone.psd");
        setValue(value);
    }
}
