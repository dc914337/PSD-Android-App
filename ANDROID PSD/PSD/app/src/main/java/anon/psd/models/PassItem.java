package anon.psd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry on 10.07.2015.
 */

/*
* C# naming convention because deserializing json serialized in c#
*/
public class PassItem
{
    @SerializedName("Id")
    public short id;    //was unsigned short. i don't like java so much.
    @SerializedName("Title")
    public String title;
    @SerializedName("Login")
    public String login;
    @SerializedName("EnterWithLogin")
    public boolean enterWithLogin;
    @SerializedName("Pass")
    public byte[] pass;
    @SerializedName("Description")
    public String description;

    public byte[] getPasswordBytes()
    {
        return pass;
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
