package vn.edu.patterrnsdemo.behavioral.interpreter;

/**
 * Component chung của cây cú pháp trừu tượng (AST).
 */
@FunctionalInterface
public interface Expression {

    boolean interpret(OrderContext context);
}
