package vn.edu.patterrnsdemo.behavioral.Strategy;

public class EconomicalShipping implements ShippingStrategy
{
    @Override
    public double calculateFee(double distanceKm)
    {
        // Phí cơ bản 7.000đ + 3.000đ/km
        return 7000 + distanceKm * 3000;
    }

    @Override
    public String getStrategyName()
    {
        return "Giao hàng tiết kiệm";
    }
}