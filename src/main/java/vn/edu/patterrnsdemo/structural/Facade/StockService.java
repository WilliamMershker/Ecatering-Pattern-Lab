package vn.edu.patterrnsdemo.structural.Facade;

public class StockService
{
    public String deductStock(String cartId)
    {
        return "   -> [StockService] Đã trừ nguyên liệu trong kho "
                + "theo giỏ hàng: " + cartId + "\n";
    }
}