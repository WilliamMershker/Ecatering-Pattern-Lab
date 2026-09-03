package vn.edu.patterrnsdemo.behavioral.State;

public class CookingState implements OrderState
{
    @Override
    public String handleNext(OrderContext context)
    {
        context.setState(new ShippingState());

        return "   -> [State: Đang nấu] "
                + "Món ăn đã chế biến xong. "
                + "Đơn hàng chuyển sang trạng thái Đang giao.\n";
    }

    @Override
    public String handleCancel(OrderContext context)
    {
        return "   -> [State: Đang nấu] "
                + "Hủy đơn thất bại: món ăn đang được chế biến.\n";
    }

    @Override
    public String getStateName()
    {
        return "ĐANG NẤU";
    }
}