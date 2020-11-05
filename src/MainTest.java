import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private File inputFile = new File("text/input.txt");
    private File outputFile = new File("text/output.txt");
    private List<String> mathOperations;
    private Main source = new Main();

    @BeforeEach
    void setUp() {
        mathOperations = new ArrayList<>();
        mathOperations.add("2 + 2");
        mathOperations.add("3 / 4");
        mathOperations.add("2 + 2 * 3");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testReadDataFromFile() {
        List<String> result = source.readDataFromFile(inputFile);

        assertEquals(result.size(), 7);
        assertTrue(result.contains("-(2 + (0) * 2)"));
        assertTrue(result.contains("2 + 2"));
        assertTrue(result.contains("2 + -2 * 5"));
        assertTrue(result.contains("3 / 4"));
        assertTrue(result.contains("2 + 2 * 3 - 10"));
        assertTrue(result.contains("2 + (2 / -(2 - 1) * -4)"));
        assertTrue(result.contains("-11.2 - -7.20"));
    }

    @Test
    void parseMultiply() {
        String expected1 = "4.0";
        String expected2 = "-6.0";
        String expected3 = "4.5";

        String result1 = source.parseMult("2 * 2");
        String result2 = source.parseMult("3 * -2");
        String result3 = source.parseMult("2.25 * 2");

        assertEquals(Double.parseDouble(expected1), Double.parseDouble(result1), 000000001);
        assertEquals(Double.parseDouble(expected2), Double.parseDouble(result2), 000000001);
        assertEquals(Double.parseDouble(expected3), Double.parseDouble(result3), 000000001);
    }

    @Test
    void parseDividing() {
        String expected1 = "-1";
        String expected2 = "0.4";
        String expected3 = "3.33333333";

        String result1 = source.parseDiv("-7 / 7");
        String result2 = source.parseDiv("2 / 5");
        String result3 = source.parseDiv("10 / 3");

        assertEquals(Double.parseDouble(expected1), Double.parseDouble(result1), 0.00000001);
        assertEquals(Double.parseDouble(expected2), Double.parseDouble(result2), 0.00000001);
        assertEquals(Double.parseDouble(expected3), Double.parseDouble(result3), 0.00000001);
    }

    @Test
    void parseDif() {
        String expected1 = "-10";
        String expected2 = "7";
        String expected3 = "-4";

        String result1 = source.parseDif("10 - 20");
        String result2 = source.parseDif("0 - -7");
        String result3 = source.parseDif("-11.2 - -7.20");

        assertEquals(Double.parseDouble(expected1), Double.parseDouble(result1), 0.00000001);
        assertEquals(Double.parseDouble(expected2), Double.parseDouble(result2), 0.00000001);
        assertEquals(Double.parseDouble(expected3), Double.parseDouble(result3), 0.00000001);
    }

    @Test
    void parseSum() {
        String expected1 = "30";
        String expected2 = "-7";
        String expected3 = "-12.4";

        String result1 = source.parseSum("10 + 20");
        String result2 = source.parseSum("0 + -7");
        String result3 = source.parseSum("-5.2 + -7.20");

        assertEquals(Double.parseDouble(expected1), Double.parseDouble(result1), 0.00000001);
        assertEquals(Double.parseDouble(expected2), Double.parseDouble(result2), 0.00000001);
        assertEquals(Double.parseDouble(expected3), Double.parseDouble(result3), 0.00000001);
    }

    @Test
    void testParseMathOperation() {
        String expected1 = "6";
        String expected2 = "-19.333333333";
        String expected3 = "-3";

        String result1 = source.parseMathExpression("2 + 2 * 2");
        String result2 = source.parseMathExpression("2 / 3 - 4 * 5");
        String result3 = source.parseMathExpression("2 * (2 - 3.5)");

        assertEquals(Double.parseDouble(expected1), Double.parseDouble(result1), 0.000000001);
        assertEquals(Double.parseDouble(expected2), Double.parseDouble(result2), 0.000000001);
        assertEquals(Double.parseDouble(expected3), Double.parseDouble(result3), 0.000000001);
    }

    @Test
    void parseBrackets() {
        String expected1 = "8";
        String expected2 = "6";
        String expected3 = "-2";

        String result1 = source.parseBrackets("((2 + 2) * 2)");
        String result2 = source.parseBrackets("(2 + (2 * 2))");
        String result3 = source.parseBrackets("-(2 + (0) * 2)");

        assertEquals(Double.parseDouble(expected1), Double.parseDouble(result1), 0.000000001);
        assertEquals(Double.parseDouble(expected2), Double.parseDouble(result2), 0.000000001);
        assertEquals(Double.parseDouble(expected3), Double.parseDouble(result3), 0.000000001);
    }
}