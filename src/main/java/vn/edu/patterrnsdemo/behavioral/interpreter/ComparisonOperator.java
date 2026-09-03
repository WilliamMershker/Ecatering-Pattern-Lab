package vn.edu.patterrnsdemo.behavioral.interpreter;

/**
 * Strategy dùng để so sánh giá trị thực tế với giá trị trong quy tắc.
 * Có thể đăng ký toán tử mới mà không sửa parser hay các Expression hiện có.
 */
@FunctionalInterface
public interface ComparisonOperator {

    boolean test(Object actualValue, Object expectedValue);
}
