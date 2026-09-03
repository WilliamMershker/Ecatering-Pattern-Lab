package vn.edu.patterrnsdemo.structural.Decorator;

public class ExtraEgg extends ToppingDecorator
{
    private static final double EGG_PRICE = 7000;

    public ExtraEgg(MenuComponent baseComponent)
    {
        super(baseComponent);
    }

    @Override
    public double getPrice()
    {
        return baseComponent.getPrice() + EGG_PRICE;
    }

    @Override
    public String getName()
    {
        return baseComponent.getName() + " + Trứng ốp la";
    }
}