package dev.sevora;

/**
 * The simple calculator for the application.
 * @author Ralph Louis Gopez
 */
public class Calculator {
    private String left = "";
    private String right = "";
    private String operation = "";

    private String previousRight = "";
    private String previousOperation = "";

    private boolean hasError = false;

    public static String NUMBERS = "0123456789";
    public static String OPERATIONS = "+-x÷";
    public static String SPECIAL = "AC C % +/- .";

    /**
     * Main function to use the calculator, you can think of it as actually using a 
     * calculator in the real world where you only press a single button to activate an instruction
     * @param entry String of a single command check out the static definitions above.
     */
    public void punch(String entry) {
        // This allows us to handle the division by zero error
        if (hasError) {
            this.clear();
            hasError = false;
        }

        // This whole if-else if block figures out the right function call for the instruction
        if (Calculator.NUMBERS.indexOf(entry) != -1) { 
            String number = getNumberAtSide();
            
            // This prevents 0 from being added if 0 is the only input
            if (number.length() == 1 && number.equals("0")) {
                setNumberAtSide("");
            } 

            number = getNumberAtSide() + entry;                  
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

    /**
     * Use to get the current text of the display.
     * @return String that is basically the text that should appear if
     * the calculator had a display.
     */
    public String display() {
        if (left.length() == 0) {
            return "0";
        } else if (operation.length() == 0 || right.length() <= 0) {
            return left;
        }
        return right;
    }

    /**
     * Use to format a String number and remove unnecessary digits, usually trailing zeroes.
     * @param number This is a string that only has numbers in it, can 
     * have the decimal point or not.
     * @return String that is formatted as a number properly, removing unnecessary
     * trailing zeroes if there are any.
     */
    protected String formatStringNumber(String number) {
        String result = number;
        int decimalIndex = number.indexOf(".");
        if (decimalIndex == -1) return result;
        int safeIndex = result.length();

        // This is a smart algorithm, it reads the string in reverse and
        // when it hits a non-zero value it could determine already what part
        // needs to be preserved or not.
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

    /**
     * Use to figure out which String number should be modified at this point
     * according to the current state.
     * @return String that says whether the left side or right side of the 
     * expression to be evaluated should be modified.
     */
    private String getSide() {
        if (operation.length() > 0) {
            return "right";
        }
        return "left";
    }

    /**
     * This returns the value of the current String number that should be modified.
     * @return A String with just numbers in it.
     */
    private String getNumberAtSide() {
        if (getSide() == "left") return left;
        return right;
    }

    /**
     * Use to set the number that should be modified.
     * @param number String that consists of numerical values only.
     */
    private void setNumberAtSide(String number) {
        if (getSide() == "left") {
            left = number;
        } else {
            right = number;
        }
    }

    /**
     * This just sets the current operation.
     * @param operation String that should match one of values from the
     * static operation variable.
     */
    private void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * This just evaluates the current expression. This part mainly adds
     * storing and using the history to allow using the same operation 
     * without repeating what was input.
     */
    private void evaluate() {
        if (this.previousRight.length() > 0 && this.previousOperation.length() > 0) {
            this.right = this.previousRight;
            this.operation = this.previousOperation;
        }
        evaluateOnce();
    }

    /**
     * This is used to evaluate the answer. It then stores the answer on the left side
     * and clears both the operation and the right side.
     */
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
                    if (right == 0) {
                        hasError = true;
                    } else {
                        result = left / right;
                    }
                    break;
            }

            if (hasError) {
                this.left = "Error";
            } else {
                this.left = formatStringNumber(String.valueOf(result));
            }

            // This is useful for repeating the same operation without having to input it.
            this.previousRight = this.right;
            this.previousOperation = this.operation;

            this.right = "";
            this.operation = "";
        }   
    }

    /**
     * Use to clear the previous operation state
     * to allow a different operation.
     */
    private void clearHistory() {
        this.previousRight = "";
        this.previousOperation = "";
    }

    /**
     * Use to evaluate a special operation, to support extra features on 
     * the calculator.
     * @param operation String of a single special
     * command check out the static definitions above. 
     */
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

    /**
     * Use to clear the whole state
     * of the calculator.
     */
    public void clear() {
        this.left = "";
        this.right = "";
        this.operation = "";
    }

    /**
     * Use to remove a single numerical value from the calculator,
     * this is literally backspace but it has some extra instruction
     * to remove the decimal point if a number next to it is being removed.
     */
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

    /**
     * Use to convert the number to a decimal, just literally adds the decimal point.
     */
    public void toggleDecimalPoint() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number.length() > 0 && number.indexOf('.') == -1) {
            setNumberAtSide(formatStringNumber(number) + '.');
        }
        
    }

    /**
     * Use to change the sign of the number, just multiplies -1 on the current value.
     */
    public void toggleSign() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number != "0") {
            double newValue = Double.parseDouble(number) * -1.0;
            String newValueString = String.valueOf(newValue);
            setNumberAtSide(formatStringNumber(newValueString));
        }
    }

    /**
     * Use to convert the number to a rate, it literally just divides the current number by 100.
     */
    public void toggleRate() {
        String number = getNumberAtSide().length() > 0 ? getNumberAtSide() : "0";

        if (number != "0") {
            double newValue = Double.parseDouble(number) / 100;
            String newValueString = String.valueOf(newValue);
            setNumberAtSide(formatStringNumber(newValueString));
        }
    }
}
