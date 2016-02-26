package anon.psd.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Dmitry on 25/02/2016.
 */
public class Strings extends HashMap<String, String> {
    public String getValueOrNull(String key)
    {
        if(!this.containsKey(key))
            return null;
        else
            return this.get(key);
    }

    public String getValueOrDefault(String key,String default_val)
    {
        if(!this.containsKey(key))
            return default_val;
        else
            return this.get(key);
    }

    public Strings getCopy() {
        Strings copy=new Strings();
        for (Entry<String,String> entry:this.entrySet())
        {
            copy.put(entry.getKey(),entry.getValue());
        }

        return copy;
    }
}
