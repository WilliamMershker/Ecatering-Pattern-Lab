package vn.edu.patterrnsdemo.behavioral.interpreter;

/**
 * Factory tạo Terminal Expression từ toán tử và giá trị đã tách token.
 */
@FunctionalInterface
public interface TerminalExpressionFactory {

    Expression create(ComparisonOperator operator, String rawExpectedValue);
}
