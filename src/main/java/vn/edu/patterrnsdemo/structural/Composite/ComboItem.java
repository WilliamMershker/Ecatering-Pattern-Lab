package vn.edu.patterrnsdemo.structural.Composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComboItem implements MenuComponent
{
    private final String comboName;
    private final double discountRate;

    private final List<MenuComponent> children =
            new ArrayList<>();

    public ComboItem(String comboName)
    {
        this(comboName, 0.10);
    }

    public ComboItem(String comboName, double discountRate)
    {
        this.comboName = comboName;
        this.discountRate = discountRate;
    }

    public void add(MenuComponent component)
    {
        children.add(component);
    }

    public void remove(MenuComponent component)
    {
        children.remove(component);
    }

    public List<MenuComponent> getChildren()
    {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getName()
    {
        return comboName;
    }

    @Override
    public double getPrice()
    {
        double total = 0;

        for (MenuComponent component : children)
        {
            total += component.getPrice();
        }

        return total * (1 - discountRate);
    }
}