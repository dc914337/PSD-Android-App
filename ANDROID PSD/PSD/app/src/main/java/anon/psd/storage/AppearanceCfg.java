package anon.psd.storage;

import java.io.File;

import anon.psd.models.AppearancesList;
import anon.psd.serializers.Serializer;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class AppearanceCfg
{
    AppearancesList _passwordAppearances = new AppearancesList();
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
        AppearancesList passes = Serializer.deserializePasswordAppearances(serialized);
        if (passes == null)
            return false;
        _passwordAppearances = (AppearancesList) passes;
        return true;
    }

    public boolean rewrite()
    {
        //save cfg
        String serializedString = Serializer.serializePasswordAppearances(_passwordAppearances);
        return FileWorker.writeFile(serializedString.getBytes(), _cfgFile);
    }


    public AppearancesList getPassesAppearances()
    {
        return _passwordAppearances;
    }
}
