package anon.psd.models;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class PassItem
{
    public short Id;    //was unsigned short. i don't like java so much.
    public String Title;
    public String Login;
    public boolean EnterWithLogin;
    public byte[] Pass;
    public String Description;

}
