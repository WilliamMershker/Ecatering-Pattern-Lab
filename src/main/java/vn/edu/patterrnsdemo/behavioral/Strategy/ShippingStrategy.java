package vn.edu.patterrnsdemo.behavioral.Strategy;

public interface ShippingStrategy
{
    double calculateFee(double distanceKm);
    String getStrategyName();
}