package vn.edu.patterrnsdemo.creational.abstractfactory;

public class AsianMenuFactory implements MenuFactory
{
    @Override
    public MenuItem createFood()
    {
        return new AsianFood();
    }

    @Override
    public MenuItem createDrink()
    {
        return new AsianDrink();
    }
}