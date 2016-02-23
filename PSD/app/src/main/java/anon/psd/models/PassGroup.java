package anon.psd.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry on 23/02/2016.
 */
public class PassGroup {

    @SerializedName("UUID")
    public String uuid;

    @SerializedName("Name")
    public String name;

    @SerializedName("Notes")
    public String notes;

    @SerializedName("Passwords")
    public PasswordList passwords;

    @SerializedName("PassGroups")
    public PassGroupsList passGroups;

}
