package anon.psd.models.gui;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import anon.psd.models.PassItem;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class PrettyPassword
{
    //appearance cfg data
    String picPath;
    ArrayList<Date> usedDates;

    //real data
    PassItem passItem;
    Bitmap pic;

    public PrettyPassword()
    {
        //empty constructor for json
    }

    public PrettyPassword(PassItem origPass)
    {
        passItem = origPass;
        //set default pic and path
    }

    public void setPic(File newPic)
    {

    }

    public void setDefaultPic()
    {

    }
}
