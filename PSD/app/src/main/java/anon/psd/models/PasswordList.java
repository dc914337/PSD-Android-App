package anon.psd.models;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class PasswordList extends TreeMap<Short, PassItem>
{
    public PasswordList getCopyWithoutPasswords()
    {
        PasswordList passes = new PasswordList();
        for (Map.Entry<Short, PassItem> entry : this.entrySet())
        {
            passes.put(entry.getKey(),
                    entry.getValue().getCopyWithoutPass());
        }

        return passes;
    }

}
