package vn.edu.patterrnsdemo.behavioral.TemplateMethod;

public class DeliveryOrderProcessor extends OrderProcessTemplate
{
    @Override
    protected String cook()
    {
        return "[Bước 3 - Giao đi] Chế biến: "
                + "Đầu bếp chế biến và đóng gói món ăn vào hộp giữ nhiệt.\n";
    }

    @Override
    protected String deliver()
    {
        return "[Bước 4 - Giao đi] Giao hàng: "
                + "Shipper nhận đơn và giao đến địa chỉ khách hàng.\n";
    }
}