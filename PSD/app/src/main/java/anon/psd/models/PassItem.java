package anon.psd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry on 10.07.2015.
 */

public class PassItem
{
    @SerializedName("PsdId")
    private short psdId;    //was unsigned short. i don't like java so much.

    @SerializedName("UUID")
    private String uuid;

    @SerializedName("Tags")
    private String[] tags;

    @SerializedName("Pass")
    private byte[] passwordBytes;

    @SerializedName("Strings")
    private Strings strings;

    public short getPsdId()
    {
        return psdId;
    }

    public String getUuid() {
        return uuid;
    }

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

    public String getString(String key)
    {
        return strings.getValueOrNull(key);
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
        item.tags = this.tags.clone();
        item.strings = this.strings.getCopy();

        return item;
    }



}
