package vn.edu.patterrnsdemo.behavioral.Observer;

public class DriverApp implements OrderObserver
{
    private String driverName;

    public DriverApp(String driverName)
    {
        this.driverName = driverName;
    }

    @Override
    public String update(String orderStatus)
    {
        return "   -> [App Tài Xế - " + driverName
                + "] Trạng thái đơn hàng: "
                + orderStatus + "\n";
    }

    @Override
    public String getObserverName()
    {
        return "Ứng dụng tài xế";
    }
}