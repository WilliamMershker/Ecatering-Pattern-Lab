package vn.edu.patterrnsdemo.behavioral.Mediator;

public class DriverComponent
{
    private final OrderMediatorHub mediator;

    public DriverComponent(OrderMediatorHub mediator)
    {
        this.mediator = mediator;
    }

    public String reportArrived()
    {
        return mediator.sendNotification(
                "DRIVER",
                "Tài xế đã đến điểm giao hàng."
        );
    }

    public String receiveMessage(String message)
    {
        return "   -> [Tài xế] Nhận thông tin: "
                + message
                + "\n";
    }
}