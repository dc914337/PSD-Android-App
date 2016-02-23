package anon.psd.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Dmitry on 10.07.2015.
 */

public class PassItem
{
    @SerializedName("Id")
    public short id;    //was unsigned short. i don't like java so much.

    @SerializedName("UUID")
    public String uuid;

    @SerializedName("Tags")
    public String[] Tags;

    @SerializedName("Pass")
    private byte[] passwordBytes;

    @SerializedName("Strings")
    private Map<String, String> strings;


    public String title;
    public String login;
    public String description;
    public boolean enterWithLogin;


    public Password getPassword()
    {
        return new Password(passwordBytes);
    }
    public byte[] getPasswordBytes()
    {
        return passwordBytes;
    }

    public PassItem getCopyWithoutPass()
    {
        PassItem item = new PassItem();
        item.id = this.id;
        item.title = this.title;
        item.login = this.login;
        item.enterWithLogin = this.enterWithLogin;
        /*item.pass = new byte[this.pass.length];
        System.arraycopy(this.pass, 0, item.pass, 0, this.pass.length);*/
        item.description = description;
        return item;
    }
}
