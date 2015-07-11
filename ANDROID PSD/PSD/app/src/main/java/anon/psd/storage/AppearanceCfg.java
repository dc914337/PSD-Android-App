package anon.psd.storage;

import java.io.File;
import java.util.ArrayList;

import anon.psd.models.gui.PrettyPassword;
import anon.psd.serializers.Serializer;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class AppearanceCfg
{
    ArrayList<PrettyPassword> _passwordAppearances = new ArrayList<>();
    File _cfgFile;

    public AppearanceCfg(File cfgFile)
    {
        _cfgFile = cfgFile;
        //if not exists create empty
        if (!cfgFile.exists()) {
            rewrite();//creates empty cfg
        }

    }

    public boolean update()
    {
        //load cfg
        byte[] data = FileWorker.readFromFile(_cfgFile);
        if (data == null)
            return false;
        String serialized = new String(data);
        ArrayList<PrettyPassword> passes = Serializer.deserializePasswordAppearances(serialized);
        if (passes == null)
            return false;
        _passwordAppearances = passes;
        return true;
    }

    public boolean rewrite()
    {
        //save cfg
        String serializedString = Serializer.serializePasswordAppearances(_passwordAppearances);
        return FileWorker.writeFile(serializedString.getBytes(), _cfgFile);
    }


    public ArrayList<PrettyPassword> getPassesAppearances()
    {
        return _passwordAppearances;
    }
}
