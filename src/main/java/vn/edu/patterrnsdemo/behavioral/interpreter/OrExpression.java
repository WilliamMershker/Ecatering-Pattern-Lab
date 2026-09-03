package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.util.Objects;

/** Non-terminal Expression: left OR right. */
public final class OrExpression implements Expression {

    private final Expression left;
    private final Expression right;

    public OrExpression(Expression left, Expression right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public boolean interpret(OrderContext context) {
        // Toán tử || giúp short-circuit: vế phải không chạy khi vế trái true.
        return left.interpret(context) || right.interpret(context);
    }
}
