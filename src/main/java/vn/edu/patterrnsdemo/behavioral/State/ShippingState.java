package vn.edu.patterrnsdemo.behavioral.State;

public class ShippingState implements OrderState
{
    @Override
    public String handleNext(OrderContext context)
    {
        return "   -> [State: Đang giao] "
                + "Shipper đã giao đơn hàng thành công.\n";
    }

    @Override
    public String handleCancel(OrderContext context)
    {
        return "   -> [State: Đang giao] "
                + "Hủy đơn thất bại: đơn hàng đã được giao cho Shipper.\n";
    }

    @Override
    public String getStateName()
    {
        return "ĐANG GIAO";
    }
}
