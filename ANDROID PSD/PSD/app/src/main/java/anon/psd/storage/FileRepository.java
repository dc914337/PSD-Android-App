package anon.psd.storage;

import java.io.File;

import anon.psd.crypto.BaseCrypto;
import anon.psd.crypto.KeyGenerator;
import anon.psd.models.DataBase;
import anon.psd.serializers.Serializer;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class FileRepository
{
    private DataBase _base;

    private String _path;
    private byte[] _userPass;

    private byte[] _encryptedData;

    public boolean setBasePath(String path)
    {
        //todo check path. check if file exists
        _path = path;
        return false;
    }

    public FileRepository(String path)
    {
        setBasePath(path);
    }

    public void setUserPass(String pass)
    {
        _userPass = KeyGenerator.getBasekeyFromUserkey(pass);
    }

    public boolean rewrite()
    {
        throw new UnsupportedOperationException();//not implemented exception
        //return false;
    }

    public boolean isLoaded()
    {
        return _base != null && _base.BTKey != null;
    }

    public DataBase getPassesBase()
    {
        return _base;
    }

    public boolean checkConnection()
    {
        return loadData();
    }

    public boolean update()
    {
        if (!loadData())
            return false;

        byte[] decoded = new BaseCrypto(_userPass).decryptAll(_encryptedData);
        if (decoded == null)
            return false;//invalid key or broken file
        String jsonData = new String(decoded);
        DataBase base = Serializer.deserializeDataBase(jsonData);
        _base = base;
        return true;
    }


    private boolean loadData()
    {
        _encryptedData = FileWorker.readFromFile(new File(_path));
        if (_encryptedData == null)
            return false;
        return true;
    }

}
