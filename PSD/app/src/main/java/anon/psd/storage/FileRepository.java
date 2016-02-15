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
        return true;
    }

    public FileRepository(String path)
    {
        setBasePath(path);
    }

    public void setDbPass(byte[] pass)
    {
        dbPass = pass;
    }

    public boolean updateKeys(byte[] newBtKey, byte[] newHBtKey)
    {
        base.btKey = newBtKey;
        base.hBTKey = newHBtKey;
        return rewrite();
    }


    public boolean rewrite()
    {
        byte[] data = Serializer.serializeDataBase(base).getBytes();
        encryptedData = new BaseCrypto(dbPass).encryptAll(data);
        return writeData();
    }

    private boolean writeData()
    {
        if (encryptedData == null)
            return false;
        return FileWorker.writeFile(encryptedData, new File(path));
    }


    public boolean isLoaded()
    {
        return base != null && base.btKey != null;
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
