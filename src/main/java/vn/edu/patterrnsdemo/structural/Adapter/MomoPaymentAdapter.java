package vn.edu.patterrnsdemo.structural.Adapter;

public class MomoPaymentAdapter implements OrderPayment
{
    // Adaptee - SDK MoMo bên thứ ba
    private final ThirdPartyMomoAPI momoSdk;

    public MomoPaymentAdapter()
    {
        this.momoSdk = new ThirdPartyMomoAPI();
    }

    @Override
    public String executePay(double amount)
    {
        // Chuyển đổi kiểu dữ liệu:
        // double của E-Catering -> int của MoMo SDK
        int convertedMoney = (int) Math.round(amount);

        StringBuilder result = new StringBuilder();

        result.append("[Adapter] Nhận yêu cầu thanh toán: ")
                .append(amount)
                .append("đ\n");

        result.append("[Adapter] Chuyển đổi double -> int: ")
                .append(convertedMoney)
                .append("đ\n");

        // Gọi API không tương thích của bên thứ ba
        result.append(momoSdk.makeDeposit(convertedMoney));

        return result.toString();
    }
}