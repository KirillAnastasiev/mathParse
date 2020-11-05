import java.util.ArrayDeque;
import java.util.Deque;

public class Parser {
    private String operators = "*/+-";

    public String checkNoLetters(String mathExpression) {
        if (mathExpression.isEmpty() || mathExpression.startsWith("Error:")) {
            return mathExpression;
        }

        return mathExpression.matches("^[\\s\\d\\\\+-\\\\*/\\\\(\\\\)]*$") ?
                mathExpression : "Error: none mathematical symbols in expression " + mathExpression;
    }

    public String checkBrackets(String mathExpression) {
        if (mathExpression.isEmpty() || mathExpression.startsWith("Error:")) {
            return mathExpression;
        }

        int bracketCounter = 0;

        for (int i = 0; i < mathExpression.length(); i++) {
            if (bracketCounter < 0) {
                return "Error: illegal brackets in math expression: " + mathExpression;
            }
            if (mathExpression.charAt(i) == '(') {
                bracketCounter++;
            } else if (mathExpression.charAt(i) == ')') {
                bracketCounter--;
            }
        }

        if (bracketCounter != 0) {
            return "Error: illegal brackets in math expression: " + mathExpression;
        }

        return mathExpression;
    }

    public String checkDoubleOperations(String mathExpression) {
        mathExpression = mathExpression.replaceAll("\\+( )*-", "-");
        mathExpression = mathExpression.replaceAll("-( )*-", "+");
        mathExpression = mathExpression.replaceAll("-( )*\\+", "-");

        return mathExpression;
    }

    public String calculateExpression(String mathExpression) {
        if (mathExpression.isEmpty() || mathExpression.startsWith("Error:")) {
            return mathExpression;
        }

        String result = null;
        Deque<String> stackOfElements = new ArrayDeque<>();

        String[] elements = mathExpression.split("\\s");
        for (int i = 0; i < elements.length; i++) {
            if (operators.contains(elements[i])) {
                double num2 = stackOfElements.isEmpty() ? 0 : Double.parseDouble(stackOfElements.pollLast());
                double num1 = stackOfElements.isEmpty() ? 0 : Double.parseDouble(stackOfElements.pollLast());
                stackOfElements.add(calc(num1, num2, elements[i]));
            } else {
                stackOfElements.add(elements[i]);
            }
        }

        result = stackOfElements.pollLast();
        if (result.contains("Infinity")) {
            return "Error: dividing by zero in expression: " + mathExpression;
        }
        return result;
    }

    private String calc(double num1, double num2, String mathOperator) {
        String result = null;
        switch (mathOperator) {
            case "*":
                result = String.valueOf(num1 * num2);
                break;

            case "/":
                result = String.valueOf(num1 / num2);
                break;

            case "+":
                result = String.valueOf(num1 + num2);
                break;

            case "-":
                result = String.valueOf(num1 - num2);
                break;
        }
        return result;
    }

    public String parseToPolandNotation(String mathExpression) {
        if (mathExpression.isEmpty() || mathExpression.startsWith("Error:")) {
            return mathExpression;
        }

        StringBuilder result = new StringBuilder();
        Deque<Character> stackOfOperators = new ArrayDeque<>();

        StringBuilder resultedNumber = new StringBuilder();
        for (int i = 0; i < mathExpression.length(); i++) {
            if (mathExpression.charAt(i) == ' ') {
                continue;
            }

            if (operators.contains(String.valueOf(mathExpression.charAt(i)))) {
                if (resultedNumber.length() > 0) {
                    result.append(resultedNumber).append(" ");
                    resultedNumber.setLength(0);
                }

                char currentOperator = mathExpression.charAt(i);

                if (stackOfOperators.isEmpty()) {
                    stackOfOperators.add(currentOperator);
                } else if (operatorPriority(stackOfOperators.peekLast()) < operatorPriority(currentOperator)) {
                    stackOfOperators.add(currentOperator);
                } else {
                    while (!stackOfOperators.isEmpty() && operatorPriority(stackOfOperators.peekLast())
                            >= operatorPriority(currentOperator)) {
                        char inStack = stackOfOperators.pollLast();
                        if (inStack != '(') {
                            result.append(inStack).append(" ");
                        }
                    }
                    stackOfOperators.add(currentOperator);
                }

            } else if(mathExpression.charAt(i) == '(') {
                stackOfOperators.add('(');
            } else if (mathExpression.charAt(i) == ')') {
                if (resultedNumber.length() > 0) {
                    result.append(resultedNumber).append(" ");
                    resultedNumber.setLength(0);
                }

                while (!stackOfOperators.isEmpty() && stackOfOperators.peekLast() != '(') {
                    char inStack = stackOfOperators.pollLast();
                    if (inStack != '(') {
                        result.append(inStack).append(" ");
                    }
                }

                if (stackOfOperators.peekLast() == '(') {
                    stackOfOperators.pollLast();
                }
            } else {
                resultedNumber.append(mathExpression.charAt(i));
            }
        }

        if (resultedNumber.length() > 0) {
            result.append(resultedNumber).append(" ");
            resultedNumber.setLength(0);
        }

        while (!stackOfOperators.isEmpty()) {
            char inStack = stackOfOperators.pollLast();
            if (inStack != '(') {
                result.append(inStack).append(" ");
            }
        }

        return result.toString();
    }

    private int operatorPriority(char mathOperator) {
        int result = 0;

        switch (mathOperator) {
            case '*':
            case '/':
                result = 3;
                break;

            case '+':
            case '-':
                result = 2;
                break;

            case '(':
            case ')':
                result = 1;
                break;
        }

        return result;
    }
}
