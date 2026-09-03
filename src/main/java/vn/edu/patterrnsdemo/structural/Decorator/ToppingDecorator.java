package vn.edu.patterrnsdemo.structural.Decorator;

public abstract class ToppingDecorator implements MenuComponent
{
    protected final MenuComponent baseComponent;

    public ToppingDecorator(MenuComponent baseComponent)
    {
        this.baseComponent = baseComponent;
    }

    @Override
    public double getPrice()
    {
        return baseComponent.getPrice();
    }

    @Override
    public String getName()
    {
        return baseComponent.getName();
    }
}