package vn.edu.patterrnsdemo.behavioral.Strategy;

public class DeliveryFeeCalculator
{
    private ShippingStrategy currentStrategy;

    public void setStrategy(ShippingStrategy strategy)
    {
        this.currentStrategy = strategy;
    }

    public double calculate(double distanceKm)
    {
        if (currentStrategy == null)
        {
            throw new IllegalStateException(
                    "Chưa cấu hình chiến lược giao hàng."
            );
        }

        return currentStrategy.calculateFee(distanceKm);
    }

    public String getCurrentStrategyName()
    {
        if (currentStrategy == null)
        {
            return "Chưa chọn";
        }

        return currentStrategy.getStrategyName();
    }
}