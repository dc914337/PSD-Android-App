package anon.psd.gui.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

public class PathPreference extends Preference
{
    public PathPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void onClick()
    {
        //call open file dialog
        //set value to opened file
        setSummary("/home/sdcard0/PSD/phonePsdBase.psd");
    }
}
