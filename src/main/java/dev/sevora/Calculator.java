package dev.sevora;

public class Calculator {
    String left = "";
    String right = "";
    String operation = "";

    String previousRight = "";
    String previousOperation = "";

    static String NUMBERS = "0123456789";
    static String OPERATIONS = "+-x÷";
    static String SPECIAL = "AC C % +/- .";

    public Calculator() {

    }

    // used to punch a key on the calculator
    public void punch(String entry) {
        if (Calculator.NUMBERS.indexOf(entry) != -1) {
            String number = getNumberAtSide();
            number += entry;
            setNumberAtSide(number);
        } else if (Calculator.OPERATIONS.indexOf(entry) != -1) {
            setOperation(entry);
            clearHistory();
        } else if (Calculator.SPECIAL.contains(entry)) {
            evaluateSpecialOperation(entry);
        } else if ( entry.equals("=") ) {
            evaluate();
        }
    }

    // used to get the display of the calculator
    public String display() {
        if (left.length() == 0) {
            return "0";
        } else if (operation.length() == 0 || right.length() <= 0) {
            return left;
        }
        return right;
    }

    // removes trailing zeroes (after decimal) and decimal point if those are all 0s
    // so for example:
    // 0.1025 -> 0.1025
    // 0.1300 -> 0.13
    // 50-> 50
    // 50.00 -> 50
    private String formatStringNumber(String number) {
        String result = number;
        int decimalIndex = number.indexOf(".");
        if (decimalIndex == -1) return result;
        int safeIndex = result.length();

        for (int index = number.length() - 1; index >= decimalIndex; --index) {
            if ( !(number.charAt(index) == '0') || (number.charAt(index) == '.') ) {
                if (index == decimalIndex) {
                    safeIndex = index;
                    break;
                }
                safeIndex = index + 1;
                break;
            }
        }

        return result.substring(0, safeIndex);
    }

    // used to get which side is active: left or right
    private String getSide() {
        if (operation.length() > 0) {
            return "right";
        }
        return "left";
    }

    // used to get number on active side
    private String getNumberAtSide() {
        if (getSide() == "left") return left;
        return right;
    }

    // used to set number on active side
    private void setNumberAtSide(String number) {
        if (getSide() == "left") {
            left = number;
        } else {
            right = number;
        }
    }

    private void setOperation(String operation) {
        this.operation = operation;
    }

    private void evaluate() {
        if (this.previousRight.length() > 0 && this.previousOperation.length() > 0) {
            this.right = this.previousRight;
            this.operation = this.previousOperation;
        }
        evaluateOnce();
    }

    private void evaluateOnce() {
        if (this.left.length() > 0 && this.right.length() > 0 && this.operation.length() > 0) {
            double left = Double.parseDouble(this.left);
            double right = Double.parseDouble(this.right);
            double result = 0.0;

            switch (operation) {
                case "+":
                    result = left + right;
                    break;
                case "-":
                    result = left - right;
                    break;
                case "x":
                    result = left * right;
                    break;
                case "÷":
                    result = left / right;
                    break;
            }

            this.left = formatStringNumber(String.valueOf(result));
            
            this.previousRight = this.right;
            this.previousOperation = this.operation;

            this.right = "";
            this.operation = "";
        }   
    }

    private void clearHistory() {
        this.previousRight = "";
        this.previousOperation = "";
    }
    
    private void evaluateSpecialOperation(String operation) {
        switch (operation) {
            case "AC":
                clear();
                break;
            case "C":
                backspace();
                break;
            case "+/-":
                toggleSign();
                break;
            case "%":
                toggleRate();
                break;
            case ".":
                toggleDecimalPoint();
                break;
        }
    }

    public void clear() {
        this.left = "";
        this.right = "";
        this.operation = "";
    }

    public void backspace() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number.length() > 0) {
            number = number.substring(0, number.length() - 1);
            if (number.length() > 1 && number.charAt(number.length() - 1) == '.') {
                number = number.substring(0, number.length() - 1);
            }
        }

        setNumberAtSide(number);
    }

    public void toggleDecimalPoint() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number.length() > 0 && number.indexOf('.') == -1) {
            setNumberAtSide(formatStringNumber(number) + '.');
        }
        
    }

    public void toggleSign() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number != "0") {
            double newValue = Double.parseDouble(number) * -1.0;
            String newValueString = String.valueOf(newValue);
            setNumberAtSide(formatStringNumber(newValueString));
        }
    }

    public void toggleRate() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number != "0") {
            double newValue = Double.parseDouble(number) / 100;
            String newValueString = String.valueOf(newValue);
            setNumberAtSide(formatStringNumber(newValueString));
        }
    }
}
