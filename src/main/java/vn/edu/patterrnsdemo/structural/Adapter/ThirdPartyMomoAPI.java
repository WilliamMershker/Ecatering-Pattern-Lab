package vn.edu.patterrnsdemo.structural.Adapter;

public class ThirdPartyMomoAPI
{
    public String makeDeposit(int money)
    {
        return "      -> [MoMo SDK] Thanh toán thành công. "
                + "Số tiền: " + money + "đ\n";
    }
}