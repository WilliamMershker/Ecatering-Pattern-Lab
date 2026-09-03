package vn.edu.patterrnsdemo.structural.Composite;

public class SingleItem implements MenuComponent
{
    private final String name;
    private final double price;

    public SingleItem(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public double getPrice()
    {
        return price;
    }
}