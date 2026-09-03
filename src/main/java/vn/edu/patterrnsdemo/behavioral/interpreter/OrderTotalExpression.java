package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.math.BigDecimal;
import java.util.Objects;

/** Terminal Expression: ORDER_TOTAL operator value. */
public final class OrderTotalExpression implements Expression {

    private final ComparisonOperator operator;
    private final BigDecimal expectedTotal;

    public OrderTotalExpression(
            ComparisonOperator operator,
            BigDecimal expectedTotal
    ) {
        this.operator = Objects.requireNonNull(operator);
        this.expectedTotal = Objects.requireNonNull(expectedTotal);
    }

    @Override
    public boolean interpret(OrderContext context) {
        Objects.requireNonNull(context, "OrderContext không được null.");
        return operator.test(context.getOrderTotal(), expectedTotal);
    }
}
