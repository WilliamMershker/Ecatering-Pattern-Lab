package vn.edu.patterrnsdemo.behavioral.interpreter;

/** Chương trình demo độc lập cho PromotionRuleInterpreter. */
public final class InterpreterDemo {

    private InterpreterDemo() {
    }

    public static void main(String[] args) {
        PromotionRuleInterpreter interpreter =
                new PromotionRuleInterpreter();

        String rule = "ORDER_TOTAL > 500000 "
                + "AND CUSTOMER_TYPE == VIP";

        Expression rootExpression = interpreter.parse(rule);

        OrderContext vipOrder = new OrderContext(
                750_000,
                "VIP",
                8
        );

        OrderContext regularOrder = new OrderContext(
                320_000,
                "REGULAR",
                3
        );

        System.out.println("QUY TẮC: " + rule);
        System.out.println(
                "Đơn VIP 750.000 đồng: "
                        + rootExpression.interpret(vipOrder)
        );
        System.out.println(
                "Đơn thường 320.000 đồng: "
                        + rootExpression.interpret(regularOrder)
        );

        String advancedRule = "NOT (CUSTOMER_TYPE == REGULAR) "
                + "AND (ORDER_TOTAL >= 500000 OR ITEM_COUNT >= 10)";

        System.out.println();
        System.out.println("QUY TẮC NÂNG CAO: " + advancedRule);
        System.out.println(
                "Kết quả trên đơn VIP: "
                        + interpreter.interpret(advancedRule, vipOrder)
        );
    }
}
