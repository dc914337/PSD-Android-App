package anon.psd.models;

import java.util.ArrayList;

import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 20.07.2015.
 */
public class AppearancesList extends ArrayList<PrettyPassword>
{
    public static AppearancesList Merge(PasswordList passItems, AppearancesList loadedAppearances)
    {
        AppearancesList mergedAppearances = new AppearancesList();

        //merge appearances by title
        for (PassItem entry : passItems) {
            PassItem currPass = entry;
            PrettyPassword currAppearance = loadedAppearances.findByUuid(currPass.getUuid());
            if (currAppearance == null)
                currAppearance = new PrettyPassword(currPass);
            else
                currAppearance.setPassItem(currPass);
            mergedAppearances.add(currAppearance);
        }

        return mergedAppearances;
    }

    public PrettyPassword findByUuid(String uuid)
    {
        for (PrettyPassword pass : this) {
            String ppUuid=pass.getUuid();
            if (ppUuid!=null &&  ppUuid.equals(uuid))
                return pass;
        }
        return null;
    }

    public PrettyPassword findById(short id)
    {
        for (PrettyPassword pass : this) {
            if (pass.getPassItem().getPsdId() == id)
                return pass;
        }
        return null;
    }

}
