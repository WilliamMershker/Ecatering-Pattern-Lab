package vn.edu.patterrnsdemo.behavioral.visitor;

import java.util.Objects;

/**
 * Đơn đặt tiệc/sự kiện.
 */
public final class CateringEventOrder implements OrderElement {
    private final String orderId;
    private final long contractAmount;
    private final long eventServiceFee;

    public CateringEventOrder(String orderId, long contractAmount, long eventServiceFee) {
        this.orderId = requireOrderId(orderId);
        this.contractAmount = requireNonNegative(contractAmount, "contractAmount");
        this.eventServiceFee = requireNonNegative(eventServiceFee, "eventServiceFee");
    }

    public String getOrderId() {
        return orderId;
    }

    public long getContractAmount() {
        return contractAmount;
    }

    public long getEventServiceFee() {
        return eventServiceFee;
    }

    public long getGrossAmount() {
        return Math.addExact(contractAmount, eventServiceFee);
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
