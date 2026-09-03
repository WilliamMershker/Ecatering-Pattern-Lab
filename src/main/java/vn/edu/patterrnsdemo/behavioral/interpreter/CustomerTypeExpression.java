package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.util.Objects;

/** Terminal Expression: CUSTOMER_TYPE operator value. */
public final class CustomerTypeExpression implements Expression {

    private final ComparisonOperator operator;
    private final String expectedCustomerType;

    public CustomerTypeExpression(
            ComparisonOperator operator,
            String expectedCustomerType
    ) {
        this.operator = Objects.requireNonNull(operator);
        this.expectedCustomerType = Objects.requireNonNull(
                expectedCustomerType
        );
    }

    @Override
    public boolean interpret(OrderContext context) {
        Objects.requireNonNull(context, "OrderContext không được null.");
        return operator.test(
                context.getCustomerType(),
                expectedCustomerType
        );
    }
}
