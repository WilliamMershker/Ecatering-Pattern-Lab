package vn.edu.patterrnsdemo.structural.Facade;

public class InvoiceService
{
    public String generateInvoice(String cartId)
    {
        return "   -> [InvoiceService] Đã tạo hóa đơn cho giỏ hàng: "
                + cartId + "\n";
    }
}