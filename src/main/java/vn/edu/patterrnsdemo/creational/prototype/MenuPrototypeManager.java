package vn.edu.patterrnsdemo.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class MenuPrototypeManager
{
    private final Map<String, MenuItemPrototype> prototypes =
            new HashMap<>();

    public void registerPrototype(
            String key,
            MenuItemPrototype prototype)
    {
        prototypes.put(key, prototype);
    }

    public MenuItemPrototype createClone(String key)
    {
        MenuItemPrototype prototype = prototypes.get(key);

        if (prototype == null)
        {
            throw new IllegalArgumentException(
                    "Không tìm thấy Prototype: " + key
            );
        }

        return prototype.clone();
    }
}