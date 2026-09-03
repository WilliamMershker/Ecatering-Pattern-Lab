package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Parser kiêm Client facade của mẫu Interpreter.
 *
 * Grammar:
 * rule       -> orExpression
 * or         -> and (OR and)*
 * and        -> unary (AND unary)*
 * unary      -> NOT unary | '(' or ')' | condition
 * condition  -> FIELD OPERATOR VALUE
 */
public final class PromotionRuleInterpreter {

    private final Map<String, ComparisonOperator> operators;
    private final Map<String, TerminalExpressionFactory> conditionFactories;

    public PromotionRuleInterpreter() {
        operators = new LinkedHashMap<>();
        conditionFactories = new LinkedHashMap<>();

        ComparisonOperators.defaultOperators()
                .forEach(this::registerOperator);

        registerCondition(
                "ORDER_TOTAL",
                (operator, value) -> new OrderTotalExpression(
                        operator,
                        parseNumber(value, "ORDER_TOTAL")
                )
        );

        registerCondition(
                "CUSTOMER_TYPE",
                CustomerTypeExpression::new
        );

        registerCondition(
                "ITEM_COUNT",
                (operator, value) -> new ItemCountExpression(
                        operator,
                        parseNumber(value, "ITEM_COUNT")
                )
        );
    }

    /**
     * Điểm mở rộng cho toán tử mới, ví dụ CONTAINS hoặc STARTS_WITH.
     */
    public PromotionRuleInterpreter registerOperator(
            String symbol,
            ComparisonOperator operator
    ) {
        String normalizedSymbol = normalizeName(symbol, "Tên toán tử");

        if (normalizedSymbol.contains("(")
                || normalizedSymbol.contains(")")) {
            throw new IllegalArgumentException(
                    "Tên toán tử không được chứa dấu ngoặc."
            );
        }

        operators.put(
                normalizedSymbol,
                Objects.requireNonNull(operator, "Operator không được null.")
        );
        return this;
    }

    /**
     * Điểm mở rộng cho điều kiện mới mà không sửa parser.
     */
    public PromotionRuleInterpreter registerCondition(
            String fieldName,
            TerminalExpressionFactory factory
    ) {
        conditionFactories.put(
                normalizeName(fieldName, "Tên trường"),
                Objects.requireNonNull(factory, "Factory không được null.")
        );
        return this;
    }

    /** Phân tích chuỗi quy tắc thành cây Expression. */
    public Expression parse(String rule) {
        if (rule == null || rule.trim().isEmpty()) {
            throw new IllegalArgumentException("Quy tắc không được rỗng.");
        }

        List<String> tokens = tokenize(rule);

        return new Parser(
                tokens,
                new LinkedHashMap<>(operators),
                new LinkedHashMap<>(conditionFactories)
        ).parse();
    }

    /** Hàm tiện ích: parse và interpret trong một lần gọi. */
    public boolean interpret(String rule, OrderContext context) {
        Objects.requireNonNull(context, "OrderContext không được null.");
        return parse(rule).interpret(context);
    }

    private static String normalizeName(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " không được rỗng.");
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static BigDecimal parseNumber(String value, String fieldName) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Giá trị của " + fieldName + " phải là số: " + value,
                    exception
            );
        }
    }

    /**
     * Tokenizer hỗ trợ:
     * - Viết liền: ORDER_TOTAL>=500000
     * - Ngoặc: ( ... )
     * - Chuỗi có nháy: CUSTOMER_TYPE == "VIP MEMBER"
     */
    private static List<String> tokenize(String source) {
        List<String> tokens = new ArrayList<>();
        int index = 0;

        while (index < source.length()) {
            char current = source.charAt(index);

            if (Character.isWhitespace(current)) {
                index++;
                continue;
            }

            if (current == '(' || current == ')') {
                tokens.add(String.valueOf(current));
                index++;
                continue;
            }

            if (current == '\'' || current == '"') {
                char quote = current;
                StringBuilder value = new StringBuilder();
                index++;
                boolean closed = false;

                while (index < source.length()) {
                    char character = source.charAt(index++);

                    if (character == '\\' && index < source.length()) {
                        value.append(source.charAt(index++));
                    } else if (character == quote) {
                        closed = true;
                        break;
                    } else {
                        value.append(character);
                    }
                }

                if (!closed) {
                    throw new IllegalArgumentException(
                            "Chuỗi giá trị chưa được đóng dấu nháy."
                    );
                }

                tokens.add(value.toString());
                continue;
            }

            if (isComparisonCharacter(current)) {
                StringBuilder operator = new StringBuilder();
                operator.append(current);
                index++;

                if (index < source.length()
                        && source.charAt(index) == '=') {
                    operator.append('=');
                    index++;
                }

                tokens.add(operator.toString());
                continue;
            }

            StringBuilder token = new StringBuilder();

            while (index < source.length()) {
                char character = source.charAt(index);

                if (Character.isWhitespace(character)
                        || character == '('
                        || character == ')'
                        || isComparisonCharacter(character)) {
                    break;
                }

                token.append(character);
                index++;
            }

            if (token.length() == 0) {
                throw new IllegalArgumentException(
                        "Không thể đọc ký tự tại vị trí " + index + "."
                );
            }

            tokens.add(token.toString());
        }

        return tokens;
    }

    private static boolean isComparisonCharacter(char character) {
        return character == '>'
                || character == '<'
                || character == '='
                || character == '!';
    }

    /** Recursive-descent parser tạo cây AST đúng độ ưu tiên toán tử. */
    private static final class Parser {

        private final List<String> tokens;
        private final Map<String, ComparisonOperator> operators;
        private final Map<String, TerminalExpressionFactory> conditionFactories;
        private int current;

        private Parser(
                List<String> tokens,
                Map<String, ComparisonOperator> operators,
                Map<String, TerminalExpressionFactory> conditionFactories
        ) {
            this.tokens = tokens;
            this.operators = operators;
            this.conditionFactories = conditionFactories;
        }

        private Expression parse() {
            Expression root = parseOr();

            if (!isAtEnd()) {
                throw error("Token không mong đợi: " + peek());
            }

            return root;
        }

        private Expression parseOr() {
            Expression expression = parseAnd();

            while (matchKeyword("OR")) {
                expression = new OrExpression(expression, parseAnd());
            }

            return expression;
        }

        private Expression parseAnd() {
            Expression expression = parseUnary();

            while (matchKeyword("AND")) {
                expression = new AndExpression(expression, parseUnary());
            }

            return expression;
        }

        private Expression parseUnary() {
            if (matchKeyword("NOT")) {
                return new NotExpression(parseUnary());
            }

            if (match("(")) {
                Expression expression = parseOr();
                consume(")", "Thiếu dấu ')' để đóng nhóm biểu thức.");
                return expression;
            }

            return parseCondition();
        }

        private Expression parseCondition() {
            String field = consume("Thiếu tên trường điều kiện.");
            String normalizedField = field.toUpperCase(Locale.ROOT);

            TerminalExpressionFactory factory =
                    conditionFactories.get(normalizedField);

            if (factory == null) {
                throw error("Trường không được hỗ trợ: " + field);
            }

            String operatorToken = consume(
                    "Thiếu toán tử sau trường " + field + "."
            );
            ComparisonOperator operator = operators.get(
                    operatorToken.toUpperCase(Locale.ROOT)
            );

            if (operator == null) {
                throw error("Toán tử không được hỗ trợ: " + operatorToken);
            }

            String expectedValue = consume(
                    "Thiếu giá trị so sánh của trường " + field + "."
            );

            if ("(".equals(expectedValue) || ")".equals(expectedValue)) {
                throw error("Giá trị so sánh không hợp lệ: " + expectedValue);
            }

            try {
                return factory.create(operator, expectedValue);
            } catch (IllegalArgumentException exception) {
                throw error(exception.getMessage());
            }
        }

        private boolean matchKeyword(String keyword) {
            if (!isAtEnd() && peek().equalsIgnoreCase(keyword)) {
                current++;
                return true;
            }
            return false;
        }

        private boolean match(String token) {
            if (!isAtEnd() && peek().equals(token)) {
                current++;
                return true;
            }
            return false;
        }

        private void consume(String expected, String message) {
            if (!match(expected)) {
                throw error(message);
            }
        }

        private String consume(String message) {
            if (isAtEnd()) {
                throw error(message);
            }
            return tokens.get(current++);
        }

        private String peek() {
            return tokens.get(current);
        }

        private boolean isAtEnd() {
            return current >= tokens.size();
        }

        private IllegalArgumentException error(String message) {
            return new IllegalArgumentException(
                    "Lỗi cú pháp tại token " + (current + 1) + ": " + message
            );
        }
    }
}
