package vn.edu.patterrnsdemo.behavioral.visitor;

/**
 * Phần tử được Visitor duyệt qua.
 *
 * Mỗi loại đơn hàng tự chuyển chính nó cho Visitor. Đây là nửa đầu của
 * cơ chế Double Dispatch.
 */
public interface OrderElement {
    void accept(OrderVisitor visitor);
}
