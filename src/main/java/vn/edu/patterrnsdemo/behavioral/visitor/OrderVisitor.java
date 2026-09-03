package vn.edu.patterrnsdemo.behavioral.visitor;

/**
 * Khai báo một thao tác riêng cho từng loại đơn hàng.
 */
public interface OrderVisitor {
    void visit(InStoreOrder order);

    void visit(DeliveryOrder order);

    void visit(CateringEventOrder order);
}
