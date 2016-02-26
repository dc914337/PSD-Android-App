package anon.psd.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Dmitry on 10.07.2015.
 */

public class PassItem
{
    @SerializedName("PsdId")
    public short psdId;    //was unsigned short. i don't like java so much.

    @SerializedName("UUID")
    public String uuid;

    @SerializedName("Tags")
    public String[] Tags;

    @SerializedName("Pass")
    private byte[] passwordBytes;

    @SerializedName("Strings")
    private Strings strings;

    public String getTitle()
    {
       return strings.getValueOrDefault("Title", "No title");
    }
    public String getLogin()
    {
        return strings.getValueOrNull("UserName");
    }
    public String getDescription()
    {
        return strings.getValueOrDefault("Notes", "");
    }

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
        item.psdId = this.psdId;
        item.uuid = this.uuid;
        item.Tags = this.Tags.clone();
        item.strings = this.strings.getCopy();

        return item;
    }
}
