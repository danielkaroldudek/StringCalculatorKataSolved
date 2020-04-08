package org.tdd.calc;

public class StringCalculator {
    public String add(String input) {
        if (input.isEmpty()) { return "0"; }
        if (isSingleNumber(input)) { return input; }

        String[] stringValues = splitInput(input);
        double sum = 0;

        for(String stringValue : stringValues) {
            sum += convertToDouble(stringValue);
        }

        if (isInteger(sum)) { return String.valueOf((int)sum); }

        return String.valueOf(convertToOneDecimalPlace(sum));
    }

    private double convertToOneDecimalPlace(double sum) {
        return Math.round(sum * 10) / 10.0;
    }

    private boolean isInteger(double sum) {
        return sum % 1 == 0;
    }

    private boolean isSingleNumber(String input) {
        return splitInput(input).length <= 1;
    }

    private String[] splitInput(String input) {
        return input.split("[,\n]");
    }

    private double convertToDouble(String stringValue) {
        return Double.parseDouble(stringValue);
    }
}
