package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.util.Objects;

/**
 * Dữ liệu của một đơn hàng được dùng khi thông dịch quy tắc.
 */
public final class OrderContext {

    private final long orderTotal;
    private final String customerType;
    private final int itemCount;

    public OrderContext(long orderTotal, String customerType, int itemCount) {
        if (orderTotal < 0) {
            throw new IllegalArgumentException("Tổng tiền đơn hàng không được âm.");
        }
        if (itemCount < 0) {
            throw new IllegalArgumentException("Số lượng sản phẩm không được âm.");
        }

        String normalizedType = Objects.requireNonNull(
                customerType, "Loại khách hàng không được null."
        ).trim();

        if (normalizedType.isEmpty()) {
            throw new IllegalArgumentException("Loại khách hàng không được rỗng.");
        }

        this.orderTotal = orderTotal;
        this.customerType = normalizedType;
        this.itemCount = itemCount;
    }

    public long getOrderTotal() {
        return orderTotal;
    }

    public String getCustomerType() {
        return customerType;
    }

    public int getItemCount() {
        return itemCount;
    }
}
