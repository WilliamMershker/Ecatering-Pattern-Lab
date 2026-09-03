package vn.edu.patterrnsdemo.behavioral.Iterator;

import java.util.ArrayList;
import java.util.List;

public class MenuCollection
{
    private final List<MenuItem> menuItems =
            new ArrayList<>();

    public void addDish(String name, double price)
    {
        menuItems.add(
                new MenuItem(name, price)
        );
    }

    public MenuIterator createIterator()
    {
        return new ListMenuIterator(menuItems);
    }
}