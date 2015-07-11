package anon.psd.storage;

import java.io.File;
import java.util.ArrayList;

import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 11.07.2015.
 */
public class AppearanceCfg
{
    ArrayList<PrettyPassword> _passwordAppearances = new ArrayList<>();
    File _cfgFile;

    public AppearanceCfg(File cfgFile)
    {
        //if not exists create empty
        if (!cfgFile.exists()) {
            rewrite();//creates empty cfg
        }
        _cfgFile = cfgFile;
    }

    public boolean update()
    {
        //load cfg
        return false;
    }

    public boolean rewrite()
    {
        //save cfg



        return false;
    }


    public ArrayList<PrettyPassword> getPassesAppearances()
    {
        return _passwordAppearances;
    }
}
