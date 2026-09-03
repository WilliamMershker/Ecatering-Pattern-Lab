package vn.edu.patterrnsdemo.behavioral.visitor;

/**
 * Chạy độc lập để quan sát Visitor trước khi gắn vào JavaFX.
 */
public final class OrderReportDemo {
    private OrderReportDemo() {
    }

    public static void main(String[] args) {
        OrderElement[] orders = {
            new InStoreOrder("IS-001", 1_200_000, 120_000),
            new DeliveryOrder("DL-001", 850_000, 40_000),
            new CateringEventOrder("CE-001", 5_000_000, 500_000)
        };

        OrderReportVisitor reportVisitor = new OrderReportVisitor();
        for (OrderElement order : orders) {
            order.accept(reportVisitor);
        }

        System.out.println(reportVisitor.getFullReport());
    }
}
