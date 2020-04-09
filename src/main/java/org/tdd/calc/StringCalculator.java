package org.tdd.calc;

import org.apache.commons.lang3.StringUtils;

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

        String customSeparator = getCustomSeparator(input);
        String[] stringValues = splitInput(input, customSeparator);
        double sum = 0;

        for(String stringValue : stringValues) {
            sum += convertToDouble(stringValue);
        }

        if (isInteger(sum)) { return String.valueOf((int)sum); }

        return String.valueOf(convertToOneDecimalPlace(sum));
    }

    private String getCustomSeparator(String input) {
        return StringUtils.substringBetween(input, "//", "\n");
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
        return input.indexOf("\n");
    }

    private boolean newLineSeparatorNextToCommaSeparator(String input) {
        return  input.contains("\n,");
    }

    private boolean commaSeparatorNextToNewLineSeparator(String input) {
        return input.contains(",\n");
    }

    private double convertToOneDecimalPlace(double sum) {
        return Math.round(sum * 10) / 10.0;
    }

    private boolean isInteger(double sum) {
        return sum % 1 == 0;
    }

    private boolean isSingleNumber(String input) {
        return input.length() == 1;
    }

    private String[] splitInput(String input, String customSeparator) {
        if (customSeparator == null || customSeparator.isEmpty()) {
            customSeparator = "[,\n]";
        } else {
            if (isVerticalSeparator(customSeparator)) {
                customSeparator = escapeVerticalBarRegexSpecialSymbol();
            }
            input = input.substring(input.indexOf("\n") + 1);
        }

        return input.split(customSeparator);
    }

    private String escapeVerticalBarRegexSpecialSymbol() {
        return "\\|";
    }

    private boolean isVerticalSeparator(String customSeparator) {
        return customSeparator.equals("|");
    }

    private double convertToDouble(String stringValue) {
        return Double.parseDouble(stringValue);
    }
}
