package anon.psd.models;

import java.util.ArrayList;
import java.util.Map;

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
            PrettyPassword currAppearance = loadedAppearances.findByTitle(currPass.title);
            if (currAppearance == null)
                currAppearance = new PrettyPassword(currPass);
            else
                currAppearance.setPassItem(currPass);
            mergedAppearances.add(currAppearance);
        }

        return mergedAppearances;
    }

    public PrettyPassword findByTitle(String title)
    {
        for (PrettyPassword pass : this) {
            if (pass.getTitle().equals(title))
                return pass;
        }
        return null;
    }

    public PrettyPassword findById(short id)
    {
        for (PrettyPassword pass : this) {
            if (pass.getPassItem().psdId == id)
                return pass;
        }
        return null;
    }

}
