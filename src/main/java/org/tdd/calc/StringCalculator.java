package org.tdd.calc;

public class StringCalculator {
    public String add(String input) {
        if (input.isEmpty()) { return "0"; }
        if (splitInput(input).length <= 1) { return input; }

        double firstNumber = convertToDouble(splitInput(input)[0]);
        double secondNumber = convertToDouble(splitInput(input)[1]);
        double sum = firstNumber + secondNumber;

        if (sum % 1 == 0) { return String.valueOf((int)sum); }

        return String.valueOf(firstNumber + secondNumber);
    }

    private String[] splitInput(String input) {
        return input.split(",");
    }

    private double convertToDouble(String stringValue) {
        return Double.parseDouble(stringValue);
    }
}
