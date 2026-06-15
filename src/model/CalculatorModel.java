package model;

public class CalculatorModel {

    private final ExpressionParser parser =
            new ExpressionParser();

    public double calculate(String expression) {
        return parser.evaluate(expression);
    }
}
