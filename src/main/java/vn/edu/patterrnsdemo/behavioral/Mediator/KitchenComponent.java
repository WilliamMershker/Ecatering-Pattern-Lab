package vn.edu.patterrnsdemo.behavioral.Mediator;

public class KitchenComponent
{
    private final OrderMediatorHub mediator;

    public KitchenComponent(OrderMediatorHub mediator)
    {
        this.mediator = mediator;
    }

    public String finishCooking()
    {
        return mediator.sendNotification(
                "KITCHEN",
                "Đơn ORD-777 đã nấu xong và sẵn sàng bàn giao."
        );
    }

    public String receiveMessage(String message)
    {
        return "   -> [Nhà bếp] Nhận thông tin: "
                + message
                + "\n";
    }
}