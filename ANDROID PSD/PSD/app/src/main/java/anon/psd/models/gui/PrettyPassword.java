package anon.psd.models.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import anon.psd.models.PassItem;
import anon.psd.storage.FileWorker;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class PrettyPassword
{
    //appearance cfg data
    String picName;
    ArrayList<Date> usedDates;

    //real data
    transient PassItem passItem;
    transient Bitmap pic;

    public PrettyPassword()
    {
        //empty constructor for json
    }

    public PrettyPassword(PassItem origPass)
    {
        passItem = origPass;
        //set default pic and path
    }

    public boolean setPic(File newPic)
    {
        //get pic
        byte[] picBytes = FileWorker.readFromFile(newPic);
        if (picBytes == null)
            return false;
        //get bitmap
        Bitmap bmp = BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
        //generate name


        //save pic and name

        return true;
        //picName.//fuck
    }

    public void setDefaultPic()
    {

    }
}
