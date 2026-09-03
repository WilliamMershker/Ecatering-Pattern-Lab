package vn.edu.patterrnsdemo.behavioral.Observer;

public class RestaurantAdminPage implements OrderObserver
{
    @Override
    public String update(String orderStatus)
    {
        return "   -> [Trang Quản Trị Nhà Hàng] "
                + "Cập nhật trạng thái đơn hàng: "
                + orderStatus + "\n";
    }

    @Override
    public String getObserverName()
    {
        return "Trang quản trị nhà hàng";
    }
}