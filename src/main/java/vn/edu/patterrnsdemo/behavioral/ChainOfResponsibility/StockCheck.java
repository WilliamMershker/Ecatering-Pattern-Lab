package vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility;

public class StockCheck extends OrderValidator
{
    @Override
    public boolean validate(
            String user,
            double amount,
            StringBuilder logBuilder)
    {
        logBuilder.append(
                "[Mắt xích 2] Kiểm tra nguyên liệu trong kho...\n"
        );

        // Giả lập kho luôn đủ với đơn <= 600.000đ
        boolean enoughStock = amount <= 600000;

        if (!enoughStock)
        {
            logBuilder.append(
                    "   -> [NGẮT CHUỖI] Nguyên liệu trong kho không đủ để xử lý đơn hàng.\n"
            );

            return false;
        }

        logBuilder.append(
                "   -> Kho đủ nguyên liệu. Chuyển sang kiểm tra số dư ví.\n"
        );

        return validateNext(
                user,
                amount,
                logBuilder
        );
    }
}