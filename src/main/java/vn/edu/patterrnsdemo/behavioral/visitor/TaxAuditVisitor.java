package vn.edu.patterrnsdemo.behavioral.visitor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Objects;

/**
 * Visitor kiểm toán thuế với mức thuế có thể cấu hình.
 *
 * Các mức mặc định chỉ dùng để minh họa mẫu thiết kế, không phải tư vấn thuế.
 */
public final class TaxAuditVisitor implements OrderVisitor {
    public static final BigDecimal DEFAULT_IN_STORE_RATE = new BigDecimal("0.08");
    public static final BigDecimal DEFAULT_DELIVERY_RATE = new BigDecimal("0.08");
    public static final BigDecimal DEFAULT_CATERING_RATE = new BigDecimal("0.10");

    private final BigDecimal inStoreRate;
    private final BigDecimal deliveryRate;
    private final BigDecimal cateringRate;

    private long taxableAmount;
    private long inStoreTax;
    private long deliveryTax;
    private long cateringTax;
    private int auditedOrderCount;

    public TaxAuditVisitor() {
        this(DEFAULT_IN_STORE_RATE, DEFAULT_DELIVERY_RATE, DEFAULT_CATERING_RATE);
    }

    public TaxAuditVisitor(BigDecimal inStoreRate,
                           BigDecimal deliveryRate,
                           BigDecimal cateringRate) {
        this.inStoreRate = requireRate(inStoreRate, "inStoreRate");
        this.deliveryRate = requireRate(deliveryRate, "deliveryRate");
        this.cateringRate = requireRate(cateringRate, "cateringRate");
    }

    @Override
    public void visit(InStoreOrder order) {
        long base = order.getGrossAmount();
        taxableAmount = Math.addExact(taxableAmount, base);
        inStoreTax = Math.addExact(inStoreTax, calculateTax(base, inStoreRate));
        auditedOrderCount++;
    }

    @Override
    public void visit(DeliveryOrder order) {
        // Quy ước demo: chỉ tiền món ăn là doanh thu chịu thuế; phí giao hàng tách riêng.
        long base = order.getFoodAmount();
        taxableAmount = Math.addExact(taxableAmount, base);
        deliveryTax = Math.addExact(deliveryTax, calculateTax(base, deliveryRate));
        auditedOrderCount++;
    }

    @Override
    public void visit(CateringEventOrder order) {
        long base = order.getGrossAmount();
        taxableAmount = Math.addExact(taxableAmount, base);
        cateringTax = Math.addExact(cateringTax, calculateTax(base, cateringRate));
        auditedOrderCount++;
    }

    public long getTaxableAmount() {
        return taxableAmount;
    }

    public long getInStoreTax() {
        return inStoreTax;
    }

    public long getDeliveryTax() {
        return deliveryTax;
    }

    public long getCateringTax() {
        return cateringTax;
    }

    public int getAuditedOrderCount() {
        return auditedOrderCount;
    }

    public long getEstimatedTax() {
        return Math.addExact(Math.addExact(inStoreTax, deliveryTax), cateringTax);
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append("[BÁO CÁO KIỂM TOÁN THUẾ]\n")
              .append("- Thuế đơn tại chỗ (8%): ").append(formatVnd(inStoreTax)).append('\n')
              .append("- Thuế đơn giao hàng (8% tiền món): ").append(formatVnd(deliveryTax)).append('\n')
              .append("- Thuế tiệc/sự kiện (10%): ").append(formatVnd(cateringTax)).append('\n')
              .append("- Doanh thu chịu thuế: ").append(formatVnd(taxableAmount)).append('\n')
              .append("- Số đơn đã kiểm toán: ").append(auditedOrderCount).append('\n')
              .append("=> THUẾ DỰ KIẾN: ").append(formatVnd(getEstimatedTax()));
        return report.toString();
    }

    private static long calculateTax(long base, BigDecimal rate) {
        return BigDecimal.valueOf(base)
                .multiply(rate)
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();
    }

    private static BigDecimal requireRate(BigDecimal rate, String fieldName) {
        Objects.requireNonNull(rate, fieldName + " không được null");
        if (rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException(fieldName + " phải nằm trong khoảng 0..1");
        }
        return rate;
    }

    private static String formatVnd(long amount) {
        return String.format(Locale.US, "%,d VND", amount).replace(',', '.');
    }
}
