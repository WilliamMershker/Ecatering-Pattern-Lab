package vn.edu.patterrnsdemo.behavioral.Observer;

public class CustomerApp implements OrderObserver
{
    private String customerName;

    public CustomerApp(String customerName)
    {
        this.customerName = customerName;
    }

    @Override
    public String update(String orderStatus)
    {
        return "   -> [App Khách Hàng - " + customerName
                + "] Trạng thái đơn hàng: "
                + orderStatus + "\n";
    }

    @Override
    public String getObserverName()
    {
        return "Ứng dụng khách hàng";
    }
}