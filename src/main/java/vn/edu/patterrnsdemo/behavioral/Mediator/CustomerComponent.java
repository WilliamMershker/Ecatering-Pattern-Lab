package vn.edu.patterrnsdemo.behavioral.Mediator;

public class CustomerComponent
{
    private final OrderMediatorHub mediator;

    public CustomerComponent(OrderMediatorHub mediator)
    {
        this.mediator = mediator;
    }

    public String requestChangeDish()
    {
        return mediator.sendNotification(
                "CUSTOMER",
                "Khách hàng yêu cầu đổi món trong đơn ORD-777."
        );
    }

    public String receiveMessage(String message)
    {
        return "   -> [Khách hàng] Nhận thông tin: "
                + message
                + "\n";
    }
}