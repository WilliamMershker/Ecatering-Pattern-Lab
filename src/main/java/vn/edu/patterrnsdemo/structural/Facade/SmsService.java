package vn.edu.patterrnsdemo.structural.Facade;

public class SmsService
{
    public String sendNotification(String userId)
    {
        return "   -> [SmsService] Đã gửi SMS xác nhận đơn hàng "
                + "cho khách hàng: " + userId + "\n";
    }
}