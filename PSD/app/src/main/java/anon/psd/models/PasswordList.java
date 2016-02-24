package anon.psd.models;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class PasswordList extends HashSet<PassItem>
{
    public PasswordList getCopyWithoutPasswords()
    {
        PasswordList passes = new PasswordList();
        for (PassItem entry : this)
        {
            passes.add(entry.getCopyWithoutPass());
        }
        return passes;
    }

    public PassItem get(short passId) {
        for (PassItem item: this)
        {
            if(item.psdId==passId)
                return item;
        }
        return null;
    }
}
