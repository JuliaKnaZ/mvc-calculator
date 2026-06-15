package model;

public class ExpressionParser {

    private String expression;
    private int position;

    public double evaluate(String expression) {

        this.expression = expression
                .replace(" ", "")
                .replace(",", ".")
                .replace("**", "^");

        this.position = 0;

        return parseExpression();
    }

    private double parseExpression() {

        double value = parseTerm();

        while (position < expression.length()) {

            char op = expression.charAt(position);

            if (op == '+' || op == '-') {

                position++;

                double next = parseTerm();

                if (op == '+') {
                    value += next;
                } else {
                    value -= next;
                }

            } else {
                break;
            }
        }

        return value;
    }

    private double parseTerm() {

        double value = parsePower();

        while (position < expression.length()) {

            if (expression.startsWith("//", position)) {

                position += 2;

                double next = parsePower();

                value = (long) value / (long) next;

                continue;
            }

            char op = expression.charAt(position);

            if (op == '*' ||
                    op == '/' ||
                    op == '%') {

                position++;

                double next = parsePower();

                switch (op) {

                    case '*':
                        value *= next;
                        break;

                    case '/':
                        value /= next;
                        break;

                    case '%':
                        value %= next;
                        break;
                }

            } else {
                break;
            }
        }

        return value;
    }

    private double parsePower() {

        double value = parseFactor();

        while (position < expression.length()) {

            char op = expression.charAt(position);

            if (op == '^') {

                position++;

                value = Math.pow(
                        value,
                        parseFactor());

            } else {
                break;
            }
        }

        return value;
    }

    private double parseFactor() {

        if (expression.charAt(position) == '(') {

            position++;

            double value = parseExpression();

            if (position < expression.length()
                    && expression.charAt(position) == ')') {

                position++;
            }

            return value;
        }

        if (expression.charAt(position) == '-') {

            position++;

            return -parseFactor();
        }

        StringBuilder number =
                new StringBuilder();

        while (position < expression.length()) {

            char c =
                    expression.charAt(position);

            if (Character.isDigit(c)
                    || c == '.') {

                number.append(c);
                position++;

            } else {
                break;
            }
        }

        return Double.parseDouble(
                number.toString());
    }
}
