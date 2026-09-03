package vn.edu.patterrnsdemo.behavioral.Mediator;

public class OrderMediatorHub
{
    private KitchenComponent kitchen;
    private DriverComponent driver;
    private CustomerComponent customer;

    public void setKitchen(KitchenComponent kitchen)
    {
        this.kitchen = kitchen;
    }

    public void setDriver(DriverComponent driver)
    {
        this.driver = driver;
    }

    public void setCustomer(CustomerComponent customer)
    {
        this.customer = customer;
    }

    public String sendNotification(String sender, String message)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("[Mediator] Nhận tín hiệu từ ")
                .append(sender)
                .append(": ")
                .append(message)
                .append("\n");

        switch (sender)
        {
            case "KITCHEN":
                sb.append("[Mediator] Điều phối thông tin đến Tài xế.\n");

                if (driver != null)
                {
                    sb.append(driver.receiveMessage(message));
                }

                break;

            case "DRIVER":
                sb.append("[Mediator] Điều phối thông tin đến Khách hàng.\n");

                if (customer != null)
                {
                    sb.append(customer.receiveMessage(message));
                }

                break;

            case "CUSTOMER":
                sb.append("[Mediator] Điều phối yêu cầu của khách đến Nhà bếp.\n");

                if (kitchen != null)
                {
                    sb.append(kitchen.receiveMessage(message));
                }

                break;

            default:
                sb.append("[Mediator] Không xác định được bên gửi.\n");
        }

        return sb.toString();
    }
}