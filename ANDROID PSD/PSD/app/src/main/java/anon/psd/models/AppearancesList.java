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
        for (Map.Entry<Short, PassItem> entry : passItems.entrySet()) {
            PassItem currPass = entry.getValue();
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

}
