package vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility;

public class BalanceCheck extends OrderValidator
{
    @Override
    public boolean validate(
            String user,
            double amount,
            StringBuilder logBuilder)
    {
        logBuilder.append(
                        "[Mắt xích 3] Kiểm tra số dư ví cho hóa đơn: "
                )
                .append(String.format("%,.0fđ", amount))
                .append("\n");

        // Giả lập số dư ví tối đa 500.000đ
        if (amount > 500000)
        {
            logBuilder.append(
                    "   -> [NGẮT CHUỖI] Số dư ví không đủ để thanh toán.\n"
            );

            return false;
        }

        logBuilder.append(
                "   -> Số dư ví hợp lệ. Đơn hàng vượt qua toàn bộ chuỗi kiểm duyệt.\n"
        );

        return validateNext(
                user,
                amount,
                logBuilder
        );
    }
}