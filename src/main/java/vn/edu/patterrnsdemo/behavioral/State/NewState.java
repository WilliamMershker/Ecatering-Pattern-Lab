package vn.edu.patterrnsdemo.behavioral.State;

public class NewState implements OrderState
{
    @Override
    public String handleNext(OrderContext context)
    {
        context.setState(new CookingState());

        return "   -> [State: Mới tạo] "
                + "Thanh toán thành công. "
                + "Đơn hàng chuyển sang trạng thái Đang nấu.\n";
    }

    @Override
    public String handleCancel(OrderContext context)
    {
        return "   -> [State: Mới tạo] "
                + "Hủy đơn thành công và tiến hành hoàn tiền.\n";
    }

    @Override
    public String getStateName()
    {
        return "MỚI TẠO";
    }
}