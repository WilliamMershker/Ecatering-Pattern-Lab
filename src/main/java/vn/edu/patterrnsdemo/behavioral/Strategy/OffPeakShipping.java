package vn.edu.patterrnsdemo.behavioral.Strategy;

public class OffPeakShipping implements ShippingStrategy
{
    @Override
    public double calculateFee(double distanceKm)
    {
        // Phí cơ bản 5.000đ + 2.500đ/km
        return 5000 + distanceKm * 2500;
    }

    @Override
    public String getStrategyName()
    {
        return "Giao hàng giờ thấp điểm";
    }
}