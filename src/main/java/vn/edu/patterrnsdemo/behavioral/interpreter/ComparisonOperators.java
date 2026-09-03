package vn.edu.patterrnsdemo.behavioral.interpreter;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Tập toán tử mặc định của ngôn ngữ quy tắc.
 */
public final class ComparisonOperators {

    public static final ComparisonOperator GREATER_THAN =
            (actual, expected) -> compareNumbers(actual, expected) > 0;

    public static final ComparisonOperator GREATER_THAN_OR_EQUAL =
            (actual, expected) -> compareNumbers(actual, expected) >= 0;

    public static final ComparisonOperator LESS_THAN =
            (actual, expected) -> compareNumbers(actual, expected) < 0;

    public static final ComparisonOperator LESS_THAN_OR_EQUAL =
            (actual, expected) -> compareNumbers(actual, expected) <= 0;

    public static final ComparisonOperator EQUAL = ComparisonOperators::equalValues;

    public static final ComparisonOperator NOT_EQUAL =
            (actual, expected) -> !equalValues(actual, expected);

    private ComparisonOperators() {
    }

    public static Map<String, ComparisonOperator> defaultOperators() {
        Map<String, ComparisonOperator> operators = new LinkedHashMap<>();
        operators.put(">", GREATER_THAN);
        operators.put(">=", GREATER_THAN_OR_EQUAL);
        operators.put("<", LESS_THAN);
        operators.put("<=", LESS_THAN_OR_EQUAL);
        operators.put("==", EQUAL);
        operators.put("!=", NOT_EQUAL);
        return operators;
    }

    private static int compareNumbers(Object actual, Object expected) {
        return toBigDecimal(actual).compareTo(toBigDecimal(expected));
    }

    private static boolean equalValues(Object actual, Object expected) {
        if (actual == null || expected == null) {
            return actual == expected;
        }

        if (actual instanceof Number && expected instanceof Number) {
            return compareNumbers(actual, expected) == 0;
        }

        return Objects.toString(actual)
                .equalsIgnoreCase(Objects.toString(expected));
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }

        try {
            return new BigDecimal(Objects.toString(value));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Giá trị không phải là số: " + value,
                    exception
            );
        }
    }
}
