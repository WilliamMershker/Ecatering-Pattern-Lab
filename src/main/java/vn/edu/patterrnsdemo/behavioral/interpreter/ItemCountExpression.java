package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.math.BigDecimal;
import java.util.Objects;

/** Terminal Expression: ITEM_COUNT operator value. */
public final class ItemCountExpression implements Expression {

    private final ComparisonOperator operator;
    private final BigDecimal expectedItemCount;

    public ItemCountExpression(
            ComparisonOperator operator,
            BigDecimal expectedItemCount
    ) {
        this.operator = Objects.requireNonNull(operator);
        this.expectedItemCount = Objects.requireNonNull(expectedItemCount);
    }

    @Override
    public boolean interpret(OrderContext context) {
        Objects.requireNonNull(context, "OrderContext không được null.");
        return operator.test(context.getItemCount(), expectedItemCount);
    }
}
