package anon.psd.background;

import anon.psd.models.DataBase;

/**
 * Created by Dmitry on 15.08.2015.
 */
public class ServiceInitObject
{
    public DataBase Base;
    public String PSDMacAddress;

    public ServiceInitObject(DataBase base, String psdMacAddress)
    {
        Base = base;
        PSDMacAddress = psdMacAddress;
    }
}
