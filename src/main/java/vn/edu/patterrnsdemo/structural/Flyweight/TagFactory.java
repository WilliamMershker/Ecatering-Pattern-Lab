package vn.edu.patterrnsdemo.structural.Flyweight;

import java.util.HashMap;
import java.util.Map;

public class TagFactory
{
    private static final Map<String, IngredientTag> cache =
            new HashMap<>();

    private TagFactory()
    {
    }

    public static IngredientTag getTag(
            String tagName,
            String iconUrl,
            String warningMessage)
    {
        IngredientTag tag = cache.get(tagName);

        if (tag == null)
        {
            tag = new IngredientTag(
                    tagName,
                    iconUrl,
                    warningMessage
            );

            cache.put(tagName, tag);
        }

        return tag;
    }

    public static int getTotalTags()
    {
        return cache.size();
    }
}