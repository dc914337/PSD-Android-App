package anon.psd.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class FileWorker
{
    public static byte[] readFromFile(File file)
    {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return bytes;
    }

    public static boolean writeFile(byte[] data, File file)
    {
        try {
            BufferedOutputStream buf = new BufferedOutputStream(new FileOutputStream(file, false));
            buf.write(data, 0, data.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
