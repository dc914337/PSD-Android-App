package anon.psd.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by Dmitry on 23/02/2016.
 */
public class PassGroup  {

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

    public PasswordList getAllSubPasses() {
        PasswordList allPasses=new PasswordList();
        allPasses.addAll(passwords);
        for(PassGroup subgroup : passGroups)
        {
            for(PassItem subgroupPass : subgroup.getAllSubPasses())
            {
                allPasses.add(subgroupPass);
            }
        }
        return allPasses;
    }
}
