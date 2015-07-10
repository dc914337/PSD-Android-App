package anon.psd.filesystem;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import anon.psd.global.Constants;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class FileWorker
{
    public static String readFromFile(String path)
    {
        File file = new File(path);

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            Log.e(Constants.LTAG, "FileWorker IO exception", e);
            return null;
        }

        return text.toString();
    }


    private static boolean writeToFile(String data)
    {
        return false;
    }
}
