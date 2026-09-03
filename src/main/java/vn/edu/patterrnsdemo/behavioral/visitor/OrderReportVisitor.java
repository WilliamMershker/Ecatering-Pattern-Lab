package vn.edu.patterrnsdemo.behavioral.visitor;

import java.util.Objects;

/**
 * Visitor tổng hợp dành cho màn hình báo cáo cuối năm.
 *
 * Mỗi lần một đơn hàng accept Visitor này, nó chuyển tiếp đúng overload sang cả
 * báo cáo doanh thu và báo cáo thuế.
 */
public final class OrderReportVisitor implements OrderVisitor {
    private final RevenueReportVisitor revenueReportVisitor;
    private final TaxAuditVisitor taxAuditVisitor;

    public OrderReportVisitor() {
        this(new RevenueReportVisitor(), new TaxAuditVisitor());
    }

    public OrderReportVisitor(RevenueReportVisitor revenueReportVisitor,
                              TaxAuditVisitor taxAuditVisitor) {
        this.revenueReportVisitor = Objects.requireNonNull(
                revenueReportVisitor, "revenueReportVisitor không được null");
        this.taxAuditVisitor = Objects.requireNonNull(
                taxAuditVisitor, "taxAuditVisitor không được null");
    }

    @Override
    public void visit(InStoreOrder order) {
        revenueReportVisitor.visit(order);
        taxAuditVisitor.visit(order);
    }

    @Override
    public void visit(DeliveryOrder order) {
        revenueReportVisitor.visit(order);
        taxAuditVisitor.visit(order);
    }

    @Override
    public void visit(CateringEventOrder order) {
        revenueReportVisitor.visit(order);
        taxAuditVisitor.visit(order);
    }

    public RevenueReportVisitor getRevenueReportVisitor() {
        return revenueReportVisitor;
    }

    public TaxAuditVisitor getTaxAuditVisitor() {
        return taxAuditVisitor;
    }

    public String getFullReport() {
        return "VISITOR — ORDER REPORT\n"
                + "Cơ chế: OrderElement.accept(visitor) -> visitor.visit(đúng loại đơn)\n\n"
                + revenueReportVisitor.getReport()
                + "\n\n"
                + taxAuditVisitor.getReport();
    }
}
