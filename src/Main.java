import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Main {
    private List<String> mathExpressions;
    private List<String> mathResults;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        mathExpressions = readDataFromFile(new File("text/input.txt"));
        mathResults = new ArrayList<>();

        for (String str : mathExpressions) {
            mathResults.add(parseMathExpression(str));
        }

        writeDataToFile(new File("text/output.txt"));
    }

    protected List<String> readDataFromFile(File inputFile) {
        List<String> result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            result = reader.lines().collect(Collectors.toList());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return result;
    }

    protected void writeDataToFile(File outputFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            mathResults.forEach(writer::println);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    protected String parseMathExpression(String mathExpression) {
        while (mathExpression.contains("(") && mathExpression.contains(")")) {
            mathExpression = parseBrackets(mathExpression);
        }

        while (mathExpression.contains(" * ")) {
            mathExpression = parseMult(mathExpression);
        }

        while (mathExpression.contains(" / ")) {
            mathExpression = parseDiv(mathExpression);
        }

        while (mathExpression.contains(" - ")) {
            mathExpression = parseDif(mathExpression);
        }

        while (mathExpression.contains(" + ")) {
            mathExpression = parseSum(mathExpression);
        }

        return mathExpression.endsWith(".0") ?
                mathExpression.substring(0, mathExpression.length() - 2) : mathExpression;
    }

    protected String parseBrackets(String mathExpression) {
        Pattern bracketPattern = Pattern.compile("\\(.+\\)");
        Matcher bracketMatch = bracketPattern.matcher(mathExpression);

        while (bracketMatch.find()) {
            int start = bracketMatch.start();
            int end = bracketMatch.end();
            String expressionInBrackets = mathExpression.substring(start + 1, end - 1);
            String result = parseMathExpression(expressionInBrackets);
            mathExpression = bracketMatch.replaceFirst(result);
        }
        return mathExpression;
    }

    protected String parseMult(String mathExpression) {
        Pattern multiplyPattern = Pattern.compile("-?\\b-?\\d+(\\.\\d+)? \\* -?\\d+(\\.\\d)?+\\b");
        Matcher multiplyMatcher = multiplyPattern.matcher(mathExpression);

        while (multiplyMatcher.find()) {
            int start = multiplyMatcher.start();
            int end = multiplyMatcher.end();
            String multiplyExpression = mathExpression.substring(start, end);
            String[] splittedExpression = multiplyExpression.trim().split(" \\* ");
            double firstNumber = Double.parseDouble(splittedExpression[0]);
            double secondNumber = Double.parseDouble(splittedExpression[1]);
            double multResult = firstNumber * secondNumber;
            mathExpression = multiplyMatcher.replaceFirst(String.valueOf(multResult));
        }

        return mathExpression;
    }

    protected String parseDiv(String mathExpression) {
        Pattern dividePattern = Pattern.compile("-?\\b-?\\d+(\\.\\d+)? / -?\\d+(\\.\\d+)?\\b");
        Matcher divideMatcher = dividePattern.matcher(mathExpression);

        while (divideMatcher.find()) {
            int start = divideMatcher.start();
            int end = divideMatcher.end();
            String dividingExpression = mathExpression.substring(start, end);
            String[] splittedExpression = dividingExpression.trim().split(" / ");
            double firstNumber = Double.parseDouble(splittedExpression[0]);
            double secondNumber = Double.parseDouble(splittedExpression[1]);
            double divideResult = firstNumber / secondNumber;
            mathExpression = divideMatcher.replaceFirst(String.valueOf(divideResult));
        }

        return mathExpression;
    }

    protected String parseDif(String mathExpression) {
        Pattern difPattern = Pattern.compile("-?\\b\\d+(\\.\\d+)? - -?\\d+(\\.\\d+)?\\b");
        Matcher difMatcher = difPattern.matcher(mathExpression);

        while (difMatcher.find()) {
            int start = difMatcher.start();
            int end = difMatcher.end();
            String difExpression = mathExpression.substring(start, end);
            String[] splittedExpression = difExpression.trim().split(" - ");
            double firstNumber = Double.parseDouble(splittedExpression[0]);
            double secondNumber = Double.parseDouble(splittedExpression[1]);
            double difResult = firstNumber - secondNumber;
            mathExpression = difMatcher.replaceFirst(String.valueOf(difResult));
        }

        return mathExpression;
    }

    protected String parseSum(String mathExpression) {
        Pattern sumPattern = Pattern.compile("-?\\b-?\\d+(\\.\\d+)? \\+ -?\\d+(\\.\\d+)?\\b");
        Matcher sumMatcher = sumPattern.matcher(mathExpression);

        while (sumMatcher.find()) {
            int start = sumMatcher.start();
            int end = sumMatcher.end();
            String sumExpression = mathExpression.substring(start, end);
            String[] splittedExpression = sumExpression.trim().split(" \\+ ");
            double firstNumber = Double.parseDouble(splittedExpression[0]);
            double secondNumber = Double.parseDouble(splittedExpression[1]);
            double sumResult = firstNumber + secondNumber;
            mathExpression = sumMatcher.replaceFirst(String.valueOf(sumResult));
        }

        return mathExpression;
    }
}