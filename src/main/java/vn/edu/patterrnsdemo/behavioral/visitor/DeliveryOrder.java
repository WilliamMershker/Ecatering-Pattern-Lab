package vn.edu.patterrnsdemo.behavioral.visitor;

import java.util.Objects;

/**
 * Đơn giao tận nơi.
 */
public final class DeliveryOrder implements OrderElement {
    private final String orderId;
    private final long foodAmount;
    private final long deliveryFee;

    public DeliveryOrder(String orderId, long foodAmount, long deliveryFee) {
        this.orderId = requireOrderId(orderId);
        this.foodAmount = requireNonNegative(foodAmount, "foodAmount");
        this.deliveryFee = requireNonNegative(deliveryFee, "deliveryFee");
    }

    public String getOrderId() {
        return orderId;
    }

    public long getFoodAmount() {
        return foodAmount;
    }

    public long getDeliveryFee() {
        return deliveryFee;
    }

    public long getGrossAmount() {
        return Math.addExact(foodAmount, deliveryFee);
    }

    @Override
    public void accept(OrderVisitor visitor) {
        Objects.requireNonNull(visitor, "visitor không được null").visit(this);
    }

    private static String requireOrderId(String orderId) {
        Objects.requireNonNull(orderId, "orderId không được null");
        if (orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("orderId không được rỗng");
        }
        return orderId;
    }

    private static long requireNonNegative(long value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " không được âm");
        }
        return value;
    }
}
