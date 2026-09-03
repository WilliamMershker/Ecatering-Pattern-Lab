package vn.edu.patterrnsdemo.creational.factorymethod;

public class RegularFoodCreator extends FoodCreator
{
    @Override
    public MenuItem factoryMethod()
    {
        return new BeefSteak();
    }
}