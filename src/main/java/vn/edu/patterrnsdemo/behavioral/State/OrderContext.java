package vn.edu.patterrnsdemo.behavioral.State;

public class OrderContext
{
    private OrderState currentState;

    public OrderContext()
    {
        this.currentState = new NewState();
    }

    public void setState(OrderState state)
    {
        this.currentState = state;
    }

    public OrderState getCurrentState()
    {
        return currentState;
    }

    public String triggerNext()
    {
        return currentState.handleNext(this);
    }

    public String triggerCancel()
    {
        return currentState.handleCancel(this);
    }
}