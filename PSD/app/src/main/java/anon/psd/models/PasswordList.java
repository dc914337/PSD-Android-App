package anon.psd.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * Created by Dmitry on 10.07.2015.
 */
public class PasswordList extends ArrayList<PassItem>
{
    public PasswordList getCopyWithoutPasswords()
    {
        PasswordList passes = new PasswordList();
        for (PassItem item : this)
            passes.add(item.getCopyWithoutPass());
        return passes;
    }



    public PassItem get(short passId) {
        for (PassItem item: this)
        {
            if(item.getPsdId()==passId)
                return item;
        }
        return null;
    }
}
