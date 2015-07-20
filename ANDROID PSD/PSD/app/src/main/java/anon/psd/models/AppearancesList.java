package anon.psd.models;

import java.util.ArrayList;

import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 20.07.2015.
 */
public class AppearancesList extends ArrayList<PrettyPassword>
{

    public PrettyPassword findByTitile(String title)
    {
        for (PrettyPassword pass : this) {
            if (pass.getTitle().equals(title))
                return pass;
        }
        return null;
    }

}
