package main.java.com.example;

import java.util.Locale;
import java.util.Map;
import java.util.function.IntBinaryOperator;

public class Calculator {

    private static final Map<String, IntBinaryOperator> OPS = Map.of(
            "add", Integer::sum,
            "sub", (a, b) -> a - b,
            "mul", (a, b) -> a * b,
            "div", (a, b) -> {
                if (b == 0) throw new IllegalArgumentException("Division by zero");
                return a / b;
            },
            "mod", (a, b) -> {
                if (b == 0) throw new IllegalArgumentException("Modulo by zero");
                return a % b;
            },
            "pow", (a, b) -> {
                // keep it int-based; you can change to double if your project expects it
                if (b < 0) throw new IllegalArgumentException("Negative exponent not supported for int pow");
                return (int) Math.pow(a, b);
            }
    );

    public int calculate(int a, int b, String op) {
        if (op == null) throw new IllegalArgumentException("Operation cannot be null");
        String key = op.trim().toLowerCase(Locale.ROOT);

        IntBinaryOperator fn = OPS.get(key);
        if (fn == null) throw new IllegalArgumentException("Unsupported operation: " + op);

        return fn.applyAsInt(a, b);
    }
}