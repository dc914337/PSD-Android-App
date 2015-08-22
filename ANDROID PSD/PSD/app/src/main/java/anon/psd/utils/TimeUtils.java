package anon.psd.utils;

import java.util.Date;

/**
 * Created by Dmitry on 22.08.2015.
 */
public class TimeUtils
{
    public static int msBetweenDates(Date date1, Date date2)
    {
        return Math.abs((int) (date1.getTime() - date2.getTime()));
    }

}
