package vn.edu.patterrnsdemo.creational.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Order
{
    private final String customerName;
    private final List<String> itemsList;

    private final String note;
    private final String couponCode;
    private final String deliveryTime;
    private final String gpsLocation;

    // Constructor private:
    // Client không thể tạo Order trực tiếp bằng new Order(...)
    private Order(OrderBuilder builder)
    {
        this.customerName = builder.customerName;

        // Defensive Copy để đảm bảo tính bất biến
        this.itemsList = Collections.unmodifiableList(
                new ArrayList<>(builder.itemsList)
        );

        this.note = builder.note;
        this.couponCode = builder.couponCode;
        this.deliveryTime = builder.deliveryTime;
        this.gpsLocation = builder.gpsLocation;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public List<String> getItems()
    {
        return itemsList;
    }

    public String getNote()
    {
        return note;
    }

    public String getCouponCode()
    {
        return couponCode;
    }

    public String getDeliveryTime()
    {
        return deliveryTime;
    }

    public String getGpsLocation()
    {
        return gpsLocation;
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", itemsList=" + itemsList +
                ", note='" + note + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", gpsLocation='" + gpsLocation + '\'' +
                '}';
    }

    // Static Inner Builder
    public static class OrderBuilder
    {
        // Thuộc tính bắt buộc
        private final String customerName;
        private final List<String> itemsList;

        // Thuộc tính tùy chọn
        private String note = "";
        private String couponCode = "";
        private String deliveryTime = "Giao ngay";
        private String gpsLocation = "";

        public OrderBuilder(String customerName, List<String> itemsList)
        {
            this.customerName = customerName;
            this.itemsList = itemsList;
        }

        public OrderBuilder setNote(String note)
        {
            this.note = note;
            return this;
        }

        public OrderBuilder setCouponCode(String couponCode)
        {
            this.couponCode = couponCode;
            return this;
        }

        public OrderBuilder setDeliveryTime(String deliveryTime)
        {
            this.deliveryTime = deliveryTime;
            return this;
        }

        public OrderBuilder setGpsLocation(String gpsLocation)
        {
            this.gpsLocation = gpsLocation;
            return this;
        }

        public Order build()
        {
            return new Order(this);
        }
    }
}