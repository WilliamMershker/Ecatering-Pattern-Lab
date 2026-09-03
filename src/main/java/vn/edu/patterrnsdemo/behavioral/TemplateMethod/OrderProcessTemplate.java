package vn.edu.patterrnsdemo.behavioral.TemplateMethod;

public abstract class OrderProcessTemplate
{
    /**
     * Template Method:
     * Cố định thứ tự xử lý của mọi đơn hàng.
     * final ngăn lớp con thay đổi cấu trúc thuật toán.
     */
    public final String processOrder()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(receiveOrder());
        sb.append(checkPayment());
        sb.append(cook());
        sb.append(deliver());
        sb.append(closeOrder());

        return sb.toString();
    }

    // Bước cố định 1
    private String receiveOrder()
    {
        return "[Bước 1] Tiếp nhận: Hệ thống tiếp nhận thông tin đơn hàng.\n";
    }

    // Bước cố định 2
    private String checkPayment()
    {
        return "[Bước 2] Thanh toán: Kiểm tra và xác nhận trạng thái thanh toán.\n";
    }

    // Bước thay đổi tùy loại đơn
    protected abstract String cook();

    // Bước thay đổi tùy loại đơn
    protected abstract String deliver();

    // Bước cố định 5
    private String closeOrder()
    {
        return "[Bước 5] Đóng đơn: Lưu lịch sử và hoàn tất đơn hàng.\n";
    }
}