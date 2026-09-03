package vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility;

public abstract class OrderValidator
{
    protected OrderValidator nextValidator;

    public void setNext(OrderValidator nextValidator)
    {
        this.nextValidator = nextValidator;
    }

    protected boolean validateNext(
            String user,
            double amount,
            StringBuilder logBuilder)
    {
        if (nextValidator == null)
        {
            return true;
        }

        return nextValidator.validate(
                user,
                amount,
                logBuilder
        );
    }

    public abstract boolean validate(
            String user,
            double amount,
            StringBuilder logBuilder);
}