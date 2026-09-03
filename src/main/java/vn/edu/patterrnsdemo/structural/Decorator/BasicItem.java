package vn.edu.patterrnsdemo.structural.Decorator;

public class BasicItem implements MenuComponent
{
    private final String name;
    private final double price;

    public BasicItem(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice()
    {
        return price;
    }

    @Override
    public String getName()
    {
        return name;
    }
}