package vn.edu.patterrnsdemo.behavioral.State;

public interface OrderState
{
    String handleNext(OrderContext context);
    String handleCancel(OrderContext context);
    String getStateName();
}