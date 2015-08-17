package anon.psd.models;


import com.google.gson.annotations.SerializedName;

/*
* C# naming convention because deserializing json serialized in c#
*/
public class DataBase
{
    @SerializedName("BTKey")
    public byte[] btKey;
    @SerializedName("HBTKey")
    public byte[] hBTKey;
    @SerializedName("Passwords")
    public PasswordList passwords;
}
