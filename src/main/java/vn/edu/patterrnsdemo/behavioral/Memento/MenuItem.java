package vn.edu.patterrnsdemo.behavioral.Memento;

public final class MenuItem
{
    private final String name;
    private final double price;

    public MenuItem(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public MenuItem copy()
    {
        return new MenuItem(name, price);
    }

    @Override
    public String toString()
    {
        return name + " (" + String.format("%,.0fđ", price) + ")";
    }
}