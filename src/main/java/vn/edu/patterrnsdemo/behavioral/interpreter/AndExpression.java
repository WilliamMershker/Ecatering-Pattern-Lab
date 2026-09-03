package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.util.Objects;

/** Non-terminal Expression: left AND right. */
public final class AndExpression implements Expression {

    private final Expression left;
    private final Expression right;

    public AndExpression(Expression left, Expression right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public boolean interpret(OrderContext context) {
        // Toán tử && giúp short-circuit: vế phải không chạy khi vế trái false.
        return left.interpret(context) && right.interpret(context);
    }
}
