package org.tdd.calc;

import java.util.regex.Pattern;

public class StringCalculator {
    public String add(String input) {
        if (input.isEmpty()) { return "0"; }
        if (isSingleNumber(input)) { return input; }
        if (commaSeparatorNextToNewLineSeparator(input)) {
            return getExceptionMessage("\\n", input);
        }
        if (newLineSeparatorNextToCommaSeparator(input)) {
            return getExceptionMessage(",", input);
        }
        if (inputEndsWithSeparator(input)) { return "Number expected but EOF found"; }

        String[] stringValues = splitInput(input);
        double sum = 0;

        for(String stringValue : stringValues) {
            sum += convertToDouble(stringValue);
        }

        if (isInteger(sum)) { return String.valueOf((int)sum); }

        return String.valueOf(convertToOneDecimalPlace(sum));
    }

    private boolean inputEndsWithSeparator(String input) {
        return Pattern.compile("[,\n]$").matcher(input).find();
    }

    private String getExceptionMessage(String separator, String input) {
        int index = separator.equals(",") ? indexOfCommaSeparator(input) : indexOfNewLineSeparator(input);
        return "Number expected but '" +
                separator +
                "' found at position " +
                index;
    }

    private int indexOfCommaSeparator(String input) {
        return input.indexOf(',');
    }

    private int indexOfNewLineSeparator(String input) {
        return input.indexOf("\\n");
    }

    private boolean newLineSeparatorNextToCommaSeparator(String input) {
        return  input.contains("\\n,");
    }

    private boolean commaSeparatorNextToNewLineSeparator(String input) {
        return input.contains(",\\n");
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
