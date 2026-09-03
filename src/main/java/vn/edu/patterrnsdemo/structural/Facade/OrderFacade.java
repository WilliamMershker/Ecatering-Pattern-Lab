package vn.edu.patterrnsdemo.structural.Facade;

public class OrderFacade
{
    private final StockService stockService;
    private final SmsService smsService;
    private final InvoiceService invoiceService;

    public OrderFacade()
    {
        this.stockService = new StockService();
        this.smsService = new SmsService();
        this.invoiceService = new InvoiceService();
    }

    public String quickPlaceOrder(String cartId, String userId)
    {
        StringBuilder result = new StringBuilder();

        result.append("[Facade] Bắt đầu Quick Checkout...\n");

        // 1. Trừ nguyên liệu trong kho
        result.append(stockService.deductStock(cartId));

        // 2. Tạo hóa đơn
        result.append(invoiceService.generateInvoice(cartId));

        // 3. Gửi SMS thông báo
        result.append(smsService.sendNotification(userId));

        result.append("[Facade] Hoàn tất quy trình đặt hàng.\n");

        return result.toString();
    }
}