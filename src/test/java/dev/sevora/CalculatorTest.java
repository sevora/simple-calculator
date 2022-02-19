package dev.sevora;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    private Calculator calculator;

    public String useExpression(String[] expression) {
        calculator.punch("AC");

        for (String ex : expression) {
            calculator.punch(ex);
        }

        calculator.punch("=");
        return calculator.display();
    }

    @Before
    public void setup() {
        calculator = new Calculator();    
    }

    @Test
    public void addition() {
        String[] tokens1 = {"4", "2", "0", "+", "6", "9"};
        assertTrue(useExpression(tokens1).equals("489"));

        String[] tokens2 = {"4", "2", "0", "+", "6", "9", "+/-"};
        assertTrue(useExpression(tokens2).equals("351"));

        String[] tokens3 = {"5", "+/-", "+", "5" };
        assertTrue(useExpression(tokens3).equals("0"));
    }

    @Test
    public void subtraction() {
        String[] tokens1 = {"4", "2", "0", "-", "6", "9"};
        assertTrue(useExpression(tokens1).equals("351"));

        String[] tokens2 = {"5", "-", "5", "+/-"};
        assertTrue(useExpression(tokens2).equals("10"));

        String[] tokens3 = {"5", "+/-", "-", "5", "+/-"};
        assertTrue(useExpression(tokens3).equals("0"));
    }

    @Test
    public void multiplication() {
        String[] tokens1 = {"7", "x", "6"};
        assertTrue(useExpression(tokens1).equals("42"));

        String[] tokens2 = {"5", "0", "%", "x", "1", "2", "0"};
        assertTrue(useExpression(tokens2).equals("60"));

        String[] tokens3 = {"5", "0", "%", "x", "5", "0", "%"};
        assertTrue(useExpression(tokens3).equals("0.25"));
    }

    @Test
    public void division() {
        String[] tokens1 = {"5", "÷", "2"};
        assertTrue(useExpression(tokens1).equals("2.5"));

        String[] tokens2 = {"5", "0", "%", "÷", "1", "0", "0"};
        assertTrue(useExpression(tokens2).equals("0.005"));

        String[] tokens3 = {"5", "0", "%", "÷", "5", "0", "%"};
        assertTrue(useExpression(tokens3).equals("1"));
    }
}
