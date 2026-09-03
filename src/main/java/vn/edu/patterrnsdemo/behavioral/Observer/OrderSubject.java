package vn.edu.patterrnsdemo.behavioral.Observer;

import java.util.ArrayList;
import java.util.List;

public class OrderSubject
{
    private List<OrderObserver> observersList = new ArrayList<>();

    private String orderId;
    private String orderStatus;

    public OrderSubject(String orderId)
    {
        this.orderId = orderId;
    }

    public void attach(OrderObserver observer)
    {
        if (!observersList.contains(observer))
        {
            observersList.add(observer);
        }
    }

    public void detach(OrderObserver observer)
    {
        observersList.remove(observer);
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String notifyObservers()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("[Subject] Đơn hàng ")
                .append(orderId)
                .append(" chuyển trạng thái: ")
                .append(orderStatus)
                .append("\n");

        sb.append("[Subject] Thông báo đến ")
                .append(observersList.size())
                .append(" Observer...\n");

        for (OrderObserver observer : observersList)
        {
            sb.append(observer.update(orderStatus));
        }

        return sb.toString();
    }
}