package anon.psd.storage.secretBase;

import anon.psd.crypto.BaseCrypto;
import anon.psd.crypto.KeyGenerator;
import anon.psd.filesystem.FileWorker;
import anon.psd.models.DataBase;
import anon.psd.serializers.Serializer;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class FileRepository
{
    public DataBase Base;

    private String _path;
    private byte[] _userPass;


    public boolean setBasePath(String path)
    {
        //todo check path. check if file exists
        _path = path;
        return false;
    }

    public void setUserPass(String pass)
    {
        _userPass = KeyGenerator.getBasekeyFromUserkey(pass);
    }

    public boolean rewrite()
    {
        return false;
    }

    public boolean update()
    {
        byte[] read = FileWorker.readFromFile(_path);

        byte[] decoded = new BaseCrypto(_userPass).decryptAll(read);
        if (decoded == null)
            return false;//invalid key or broken file
        String jsonData = new String(decoded);
        DataBase base = Serializer.Deserialize(jsonData);

        return false;
    }

}
