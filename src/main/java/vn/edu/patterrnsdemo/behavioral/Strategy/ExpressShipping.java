package vn.edu.patterrnsdemo.behavioral.Strategy;

public class ExpressShipping implements ShippingStrategy
{
    @Override
    public double calculateFee(double distanceKm)
    {
        // Phí cơ bản 15.000đ + 5.000đ/km
        return 15000 + distanceKm * 5000;
    }

    @Override
    public String getStrategyName()
    {
        return "Giao hàng siêu tốc";
    }
}