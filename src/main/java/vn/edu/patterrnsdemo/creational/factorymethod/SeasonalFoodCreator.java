package vn.edu.patterrnsdemo.creational.factorymethod;

public class SeasonalFoodCreator extends FoodCreator
{
    @Override
    public MenuItem factoryMethod()
    {
        return new SummerSalad();
    }
}