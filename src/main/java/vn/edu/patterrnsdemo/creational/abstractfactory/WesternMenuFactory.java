package vn.edu.patterrnsdemo.creational.abstractfactory;

public class WesternMenuFactory implements MenuFactory
{
    @Override
    public MenuItem createFood()
    {
        return new WesternFood();
    }

    @Override
    public MenuItem createDrink()
    {
        return new WesternDrink();
    }
}