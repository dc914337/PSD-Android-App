package anon.psd.models;

import java.io.UnsupportedEncodingException;

import anon.psd.utils.ArraysUtils;

/**
 * Created by Dmitry on 10.07.2015.
 */

/*
* C# naming convention because deserializing json serialized in c#
*/
public class PassItem
{
    public short Id;    //was unsigned short. i don't like java so much.
    public String Title;
    public String Login;
    public boolean EnterWithLogin;
    public byte[] Pass;
    public String Description;


    public byte[] getPasswordBytes()
    {
        if (EnterWithLogin) {
            byte[] loginBytes = new byte[0];
            try {
                loginBytes = Login.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Pass;
            }
            return ArraysUtils.concatArrays(loginBytes,new byte[]{'\t'}, Pass);
        } else {
            return Pass;
        }
    }
}
