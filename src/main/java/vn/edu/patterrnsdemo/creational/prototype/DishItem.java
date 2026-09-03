package vn.edu.patterrnsdemo.creational.prototype;

public class DishItem
{
    private String name;
    private double price;

    public DishItem(String name, double price)
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

    public void setPrice(double price)
    {
        this.price = price;
    }

    // Tạo bản sao độc lập cho từng món con
    public DishItem clone()
    {
        return new DishItem(this.name, this.price);
    }

    @Override
    public String toString()
    {
        return name + " (" + price + "đ)";
    }
}