import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainPolish {
    private static File inputFile = new File("text/polakinput.txt");
    private static File outputFile = new File("text/polakoutput.txt");
    private static List<String> mathExpressions;

    public static void main(String[] args) {
        Parser parser = new Parser();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            mathExpressions = reader
                    .lines()
                    .collect(Collectors.toList());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            mathExpressions.stream()
                    .map(parser::checkNoLetters)
                    .map(parser::checkBrackets)
                    .map(parser::checkDoubleOperations)
                    .map(parser::parseToPolandNotation)
                    .map(parser::calculateExpression)
                    .forEach(writer::println);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}

