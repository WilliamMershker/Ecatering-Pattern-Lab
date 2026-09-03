package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.util.Objects;

/** Non-terminal Expression: NOT expression. */
public final class NotExpression implements Expression {

    private final Expression expression;

    public NotExpression(Expression expression) {
        this.expression = Objects.requireNonNull(expression);
    }

    @Override
    public boolean interpret(OrderContext context) {
        return !expression.interpret(context);
    }
}
