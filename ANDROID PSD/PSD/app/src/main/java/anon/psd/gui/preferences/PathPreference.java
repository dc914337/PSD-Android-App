package anon.psd.gui.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import java.io.File;

import anon.psd.gui.activities.SettingsActivity;

public class PathPreference extends Preference
{
    private final String TAG = "PathPreference";
    Context context;


    public PathPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
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

    public void setValue(File value)
    {
        persistString(value.getAbsolutePath());
        setSummary(value.getAbsolutePath());
    }

    /*
    (i don't like android anymore. I thought how to call activity from preference for too long)
    * Okay! What happens here:
    * onClick -> calls SettingsActivity and SettingsActivity remembers THIS PathPreference
    * SettingsActivity opens aFileChooser(file dialog) and handles it's result
    * after that, SettingsActivity calls setValue of remembered PathPreference(THIS) and calls
      * setValue(result)
      *
      * I don't know good solution for this but i do want to finish this code.
    */
    @Override
    public void onClick()
    {
        SettingsActivity activity = (SettingsActivity) context;
        activity.getPath(this);
    }


}
