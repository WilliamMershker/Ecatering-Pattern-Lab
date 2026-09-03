package vn.edu.patterrnsdemo.behavioral.visitor;

import java.util.Objects;

/**
 * Đơn dùng món tại cửa hàng.
 */
public final class InStoreOrder implements OrderElement {
    private final String orderId;
    private final long foodAmount;
    private final long serviceFee;

    public InStoreOrder(String orderId, long foodAmount, long serviceFee) {
        this.orderId = requireOrderId(orderId);
        this.foodAmount = requireNonNegative(foodAmount, "foodAmount");
        this.serviceFee = requireNonNegative(serviceFee, "serviceFee");
    }

    public String getOrderId() {
        return orderId;
    }

    public long getFoodAmount() {
        return foodAmount;
    }

    public long getServiceFee() {
        return serviceFee;
    }

    public long getGrossAmount() {
        return Math.addExact(foodAmount, serviceFee);
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
