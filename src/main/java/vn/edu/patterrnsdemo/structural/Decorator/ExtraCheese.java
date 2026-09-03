package vn.edu.patterrnsdemo.structural.Decorator;

public class ExtraCheese extends ToppingDecorator
{
    private static final double CHEESE_PRICE = 10000;

    public ExtraCheese(MenuComponent baseComponent)
    {
        super(baseComponent);
    }

    @Override
    public double getPrice()
    {
        return baseComponent.getPrice() + CHEESE_PRICE;
    }

    @Override
    public String getName()
    {
        return baseComponent.getName() + " + Phô mai";
    }
}