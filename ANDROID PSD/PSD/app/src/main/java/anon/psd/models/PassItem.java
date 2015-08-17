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
}
