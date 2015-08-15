package anon.psd.storage;

import java.io.File;

import anon.psd.crypto.BaseCrypto;
import anon.psd.models.DataBase;
import anon.psd.serializers.Serializer;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class FileRepository
{
    private DataBase base;

    private String path;
    private byte[] dbPass;

    private byte[] encryptedData;

    public boolean setBasePath(String path)
    {
        //todo check path. check if file exists
        this.path = path;
        return false;
    }

    public FileRepository(String path)
    {
        setBasePath(path);
    }

    public void setDbPass(byte[] pass)
    {
        dbPass = pass;
    }

    public boolean rewrite()
    {
        throw new UnsupportedOperationException();//not implemented exception
        //return false;
    }

    public boolean isLoaded()
    {
        return base != null && base.BTKey != null;
    }

    public DataBase getPassesBase()
    {
        return base;
    }

    public boolean checkConnection()
    {
        return loadData();
    }

    public boolean update()
    {
        if (!loadData())
            return false;

        byte[] decoded = new BaseCrypto(dbPass).decryptAll(encryptedData);
        if (decoded == null)
            return false;//invalid key or broken file
        String jsonData = new String(decoded);
        DataBase base = Serializer.deserializeDataBase(jsonData);
        this.base = base;
        return true;
    }


    private boolean loadData()
    {
        encryptedData = FileWorker.readFromFile(new File(path));
        if (encryptedData == null)
            return false;
        return true;
    }

    public String getBasePath()
    {
        return path;
    }
}
