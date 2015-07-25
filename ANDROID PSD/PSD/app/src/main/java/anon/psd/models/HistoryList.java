package anon.psd.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dmitry on 24.07.2015.
 */
public class HistoryList extends ArrayList<Date>
{
    public Date getLastDate()
    {
        if (this.size() == 0)
            return null;


        Date lastDate = this.get(0);
        for (Date currDate : this) {
            if (currDate.after(lastDate))
                lastDate = currDate;
        }
        return lastDate;
    }
}
