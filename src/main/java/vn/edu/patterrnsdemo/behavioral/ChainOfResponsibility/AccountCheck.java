package vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility;

public class AccountCheck extends OrderValidator
{
    @Override
    public boolean validate(
            String user,
            double amount,
            StringBuilder logBuilder)
    {
        logBuilder.append("[Mắt xích 1] Kiểm tra trạng thái tài khoản: ")
                .append(user)
                .append("\n");

        // Giả lập tài khoản bị khóa
        if (user == null
                || user.trim().isEmpty()
                || "LOCKED_USER".equalsIgnoreCase(user))
        {
            logBuilder.append(
                    "   -> [NGẮT CHUỖI] Tài khoản không hợp lệ hoặc đã bị khóa.\n"
            );

            return false;
        }

        logBuilder.append(
                "   -> Tài khoản hợp lệ. Chuyển sang kiểm tra kho.\n"
        );

        return validateNext(
                user,
                amount,
                logBuilder
        );
    }
}