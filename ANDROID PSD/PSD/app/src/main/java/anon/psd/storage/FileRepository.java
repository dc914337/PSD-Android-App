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
        throw new UnsupportedOperationException();//not implemented exception
        //return false;
    }

    public boolean update()
    {
        byte[] read = FileWorker.readFromFile(new File(_path));

        byte[] decoded = new BaseCrypto(_userPass).decryptAll(read);
        if (decoded == null)
            return false;//invalid key or broken file
        String jsonData = new String(decoded);
        DataBase base = Serializer.Deserialize(jsonData);

        return false;
    }

}
