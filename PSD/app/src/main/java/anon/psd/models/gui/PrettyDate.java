package anon.psd.models.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dmitry on 29.08.2015.
 */
public class PrettyDate extends java.util.Date
{

    public PrettyDate()
    {
        super();
    }

    private boolean isToday()
    {
        int currentDay = new Date().getDay();
        return this.getDay() == currentDay;
    }

    @Override
    public String toString()
    {
        SimpleDateFormat dt = new SimpleDateFormat("'Time: ' hh:mm:ss ' \nDate: ' yyyy-MM-dd ");

        return dt.format(this) + (isToday() ? "(today)" : "");
    }
}