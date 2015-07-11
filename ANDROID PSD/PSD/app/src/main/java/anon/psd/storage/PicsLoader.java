package anon.psd.storage;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class PicsLoader
{
    File _directoryPath;

    public PicsLoader(File directoryPath)
    {
        _directoryPath = directoryPath;
    }

    public Bitmap getPicOrDefault(String picName)
    {
        throw new UnsupportedOperationException();
    }
}
