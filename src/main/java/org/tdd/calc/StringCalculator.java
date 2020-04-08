package org.tdd.calc;

public class StringCalculator {
    public String add(String input) {
        if (input.isEmpty()) { return "0"; }
        if (splitInput(input).length <= 1) { return input; }

        int firstNumber = convertToInteger(splitInput(input)[0]);
        int secondNumber = convertToInteger(splitInput(input)[1]);

        return String.valueOf(firstNumber + secondNumber);
    }

    private String[] splitInput(String input) {
        return input.split(",");
    }

    private int convertToInteger(String stringValue) {
        return Integer.parseInt(stringValue);
    }
}
