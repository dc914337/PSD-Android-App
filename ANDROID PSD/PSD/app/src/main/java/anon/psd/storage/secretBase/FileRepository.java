package anon.psd.storage.secretBase;

import java.util.Arrays;

import anon.psd.crypto.BaseCrypto;
import anon.psd.filesystem.FileWorker;
import anon.psd.models.DataBase;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class FileRepository
{
    public DataBase Base;

    private String _path;
    private String _userPass;


    public boolean setBasePath(String path)
    {
        //todo check path. check if file exists
        _path = path;
        return false;
    }

    public void setUserPass(String pass)
    {
        _userPass = pass;
    }

    public boolean rewrite()
    {
        return false;
    }

    public boolean update()
    {
        String read = FileWorker.readFromFile(_path);
        byte[] decoded = new BaseCrypto(_userPass.getBytes()).decryptAll(read.getBytes());
        if (decoded == null)
            return false;//invalid key or broken file
        String jsonData = Arrays.toString(decoded);
        

        return false;
    }

}
