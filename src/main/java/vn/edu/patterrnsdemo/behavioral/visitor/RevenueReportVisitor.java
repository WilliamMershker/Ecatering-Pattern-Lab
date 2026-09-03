package vn.edu.patterrnsdemo.behavioral.visitor;

import java.util.Locale;

/**
 * Visitor lập báo cáo doanh thu và các khoản phí theo từng loại đơn.
 */
public final class RevenueReportVisitor implements OrderVisitor {
    private long inStoreRevenue;
    private long deliveryFoodRevenue;
    private long deliveryFeeRevenue;
    private long cateringRevenue;
    private int inStoreOrderCount;
    private int deliveryOrderCount;
    private int cateringOrderCount;

    @Override
    public void visit(InStoreOrder order) {
        inStoreRevenue = Math.addExact(inStoreRevenue, order.getGrossAmount());
        inStoreOrderCount++;
    }

    @Override
    public void visit(DeliveryOrder order) {
        deliveryFoodRevenue = Math.addExact(deliveryFoodRevenue, order.getFoodAmount());
        deliveryFeeRevenue = Math.addExact(deliveryFeeRevenue, order.getDeliveryFee());
        deliveryOrderCount++;
    }

    @Override
    public void visit(CateringEventOrder order) {
        cateringRevenue = Math.addExact(cateringRevenue, order.getGrossAmount());
        cateringOrderCount++;
    }

    public long getInStoreRevenue() {
        return inStoreRevenue;
    }

    public long getDeliveryFoodRevenue() {
        return deliveryFoodRevenue;
    }

    public long getDeliveryFeeRevenue() {
        return deliveryFeeRevenue;
    }

    public long getCateringRevenue() {
        return cateringRevenue;
    }

    public int getTotalOrderCount() {
        return inStoreOrderCount + deliveryOrderCount + cateringOrderCount;
    }

    public long getTotalRevenue() {
        long deliveryRevenue = Math.addExact(deliveryFoodRevenue, deliveryFeeRevenue);
        return Math.addExact(Math.addExact(inStoreRevenue, deliveryRevenue), cateringRevenue);
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append("[BÁO CÁO DOANH THU]\n")
              .append("- Ăn tại chỗ: ").append(formatVnd(inStoreRevenue)).append('\n')
              .append("- Món ăn giao tận nơi: ").append(formatVnd(deliveryFoodRevenue)).append('\n')
              .append("- Phí giao hàng: ").append(formatVnd(deliveryFeeRevenue)).append('\n')
              .append("- Tiệc/sự kiện: ").append(formatVnd(cateringRevenue)).append('\n')
              .append("- Số đơn đã duyệt: ").append(getTotalOrderCount()).append('\n')
              .append("=> TỔNG DOANH THU: ").append(formatVnd(getTotalRevenue()));
        return report.toString();
    }

    private static String formatVnd(long amount) {
        return String.format(Locale.US, "%,d VND", amount).replace(',', '.');
    }
}
