package org.tdd.calc;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class StringCalculator {
    public String add(String input) {
        if (input.isEmpty()) { return "0"; }
        if (isSingleNumber(input)) { return input; }

        ArrayList<String> errorMessages = new ArrayList<>();

        verifySeparator(input, errorMessages);

        String customSeparator = getCustomSeparator(input);
        input = getSubstringIfCustomSeparatorPresent(input, customSeparator);
        if (hasMultipleSeparators(customSeparator, input)) {
            errorMessages.add(getTwoSeparatorsExceptionMessage(customSeparator, input));
        }

        Double[] values = convertToDoubles(splitInput(input, customSeparator));
        if(containsNegativeNumbers(values)) {
            errorMessages.add(getNegativeNumbersExceptionMessage(values));
        }

        if(areAnyErrors(errorMessages)) {
            return getFormattedErrorMessage(errorMessages);
        }

        double sum = getSumOfTheValues(values);
        if (isInteger(sum)) { return String.valueOf((int)sum); }

        return String.valueOf(convertToOneDecimalPlace(sum));
    }

    private void verifySeparator(String input, ArrayList<String> errorMessages) {
        if (commaSeparatorNextToNewLineSeparator(input)) {
            errorMessages.add(getNumberExpectedExceptionMessage("\\n", input));
        }
        if (newLineSeparatorsNextToEachOther(input)) {
            errorMessages.add(getNumberExpectedExceptionMessage("\\n", input));
        }
        if (newLineSeparatorNextToCommaSeparator(input)) {
            errorMessages.add(getNumberExpectedExceptionMessage(",", input));
        }
        if (commaSeparatorsNextToEachOther(input)) {
            errorMessages.add(getNumberExpectedExceptionMessage(",", input));
        }
        if (inputEndsWithSeparator(input)) {
            errorMessages.add("Number expected but EOF found");
        }
    }

    private double getSumOfTheValues(Double[] values) {
        double sum = 0;

        for(double value : values) {
            sum += value;
        }

        return sum;
    }

    private String getFormattedErrorMessage(ArrayList<String> errorMessages) {
        StringBuilder errorMessage = new StringBuilder();
        for(int i = 0; i < errorMessages.size(); i++) {
            if(i > 0) { errorMessage.append("\\n"); }
            errorMessage.append(errorMessages.get(i));
        }
        return errorMessage.toString();
    }

    private boolean areAnyErrors(ArrayList<String> errorMessages) {
        return errorMessages.size() > 0;
    }

    private Double[] convertToDoubles(String[] input) {
        Collection<Double> numbers = new ArrayList<>();
        for (String stringValue : input) {
            if (stringValue.isEmpty()) { continue; }
            try {
                numbers.add(Double.parseDouble(stringValue));

            } catch (NumberFormatException e) {
                // Skipped for now
            }
        }
        return numbers.toArray(new Double[0]);
    }

    private String getNegativeNumbersExceptionMessage(Double[] values) {
        StringBuilder message = new StringBuilder("Negative not allowed :");
        Collection<Double> negatives = new ArrayList<>();

        for (double value : values) {
            if (value > 0) continue;
            negatives.add(value);
        }

        for(int i = 0; i < negatives.size(); i++) {
            double value = negatives.toArray(new Double[0])[i];
            if (i > 0) {
                message.append(",");
            }
            if (isInteger(value)) {
                message.append(" ");
                message.append((int)value);
                continue;
            }
            message.append(" ");
            message.append(value);
        }

        return message.toString();
    }

    private boolean containsNegativeNumbers(Double[] values) {
        for(double value : values) {
            if (value < 0) { return true; }
        }

        return false;
    }

    private String getSubstringIfCustomSeparatorPresent(String  input, String customSeparator) {
        if(customSeparator != null) {
            input = input.substring(input.indexOf("\n") + 1);
        }
        return input;
    }

    private String getTwoSeparatorsExceptionMessage(String customSeparator, String input) {
        return "'" + customSeparator +
                "' expected but ',' found at position " +
                indexOfCommaSeparator(input);
    }

    private boolean hasMultipleSeparators(String customSeparator, String input) {
        return (customSeparator != null) &&
                Pattern.compile("[,]").matcher(input).find();
    }

    private String getCustomSeparator(String input) {
        return StringUtils.substringBetween(input, "//", "\n");
    }

    private boolean inputEndsWithSeparator(String input) {
        return Pattern.compile("[,\n]$").matcher(input).find();
    }

    private String getNumberExpectedExceptionMessage(String separator, String input) {
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

    private boolean commaSeparatorsNextToEachOther(String input) {
        return input.contains(",,");
    }

    private boolean newLineSeparatorsNextToEachOther(String input) {
        return input.contains("\n\n");
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
        if (customSeparator == null) {
            customSeparator = "[,\n]";
        } else {
            if (isVerticalSeparator(customSeparator)) {
                customSeparator = escapeVerticalBarRegexSpecialSymbol();
            }
        }

        return input.split(customSeparator);
    }

    private String escapeVerticalBarRegexSpecialSymbol() {
        return "\\|";
    }

    private boolean isVerticalSeparator(String customSeparator) {
        return customSeparator.equals("|");
    }
}
