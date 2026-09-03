package vn.edu.patterrnsdemo.behavioral.Command;

public class CookDishCommand implements KitchenCommand
{
    private final String dishName;
    private final int quantity;
    private final Chef chefReceiver;

    public CookDishCommand(
            String dishName,
            int quantity,
            Chef chefReceiver)
    {
        this.dishName = dishName;
        this.quantity = quantity;
        this.chefReceiver = chefReceiver;
    }

    @Override
    public String execute()
    {
        return chefReceiver.cookDish(dishName, quantity);
    }
}