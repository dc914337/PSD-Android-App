package anon.psd.storage;

import java.io.File;

import anon.psd.models.AppearancesList;
import anon.psd.serializers.Serializer;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class AppearanceCfg
{
    AppearancesList passwordAppearances = new AppearancesList();
    File cfgFile;

    public AppearanceCfg(File cfgFile)
    {
        this.cfgFile = cfgFile;
        //if not exists create empty
        if (!cfgFile.exists()) {
            rewrite();//creates empty cfg
        }

    }

    public boolean update()
    {
        //load cfg
        byte[] data = FileWorker.readFromFile(cfgFile);
        if (data == null)
            return false;
        String serialized = new String(data);
        AppearancesList passes = Serializer.deserializePasswordAppearances(serialized);
        if (passes == null) {
            passwordAppearances = new AppearancesList();
            return false;
        }
        passwordAppearances = passes;
        return true;
    }

    public boolean rewrite()
    {
        //save cfg
        String serializedString = Serializer.serializePasswordAppearances(passwordAppearances);
        return FileWorker.writeFile(serializedString.getBytes(), cfgFile);
    }


    public AppearancesList getPassesAppearances()
    {
        return passwordAppearances;
    }

    public void setPassesAppearances(AppearancesList passesAppearances)
    {
        passwordAppearances = passesAppearances;
    }
}
