package anon.psd.utils;

/**
 * Created by Dmitry on 27.07.2015.
 */
public class TextWorker
{
    public static String replaceNullOrEmpty(String source, String replacement)
    {
        if (source == null || source.isEmpty() || source.equals("null"))
            return replacement;
        return source;
    }
}
