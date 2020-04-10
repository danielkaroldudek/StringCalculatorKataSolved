package org.tdd.calc;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    public String add(String input) {
        Pair<Double, List<String>> adds = adds(input);

        if(areAnyErrors(adds.getValue1())) {
            return getFormattedErrorMessage(adds.getValue1());
        }

        if (isInteger(adds.getValue0())) { return String.valueOf(adds.getValue0().intValue()); }

        return String.valueOf(convertToOneDecimalPlace(adds.getValue0()));
    }

    private Pair<Double, List<String>> adds(String input) {
        if (input.isEmpty()) { return new Pair<>(0.0, new ArrayList<>()); }
        if (isSingleNumber(input)) { return new Pair<>(Double.valueOf(input), new ArrayList<>()); }

        List<String> errorMessages = new ArrayList<>();

        verifySeparator(input, errorMessages);

        String customSeparator = getCustomSeparator(input);
        input = getSubstringIfCustomSeparatorPresent(input, customSeparator);
        if (hasMultipleSeparators(customSeparator, input)) {
            errorMessages.add(getTwoSeparatorsExceptionMessage(customSeparator, input));
        }

        List<Double> values = convertToDoubles(splitInput(input, customSeparator));
        if(containsNegativeNumbers(values)) {
            errorMessages.add(getNegativeNumbersExceptionMessage(values));
        }

        if (errorMessages.isEmpty()) {
            return new Pair<>(getSumOfTheValues(values), new ArrayList<>());
        }


        return new Pair<>(null, errorMessages);
    }

    private void verifySeparator(String input, List<String> errorMessages) {
        String[][] possibleSeparatorNextToEachOther = {
                {",\n", "\\n"}, {"\n\n", "\\n"}, {"\n,", ","}, {",,", ","}
        };

        for (String[] separator : possibleSeparatorNextToEachOther) {
            if (separatorsNextToEachOther(input, separator[0])) {
                errorMessages.add(getNumberExpectedExceptionMessage(separator[1], input));
            }
        }

        if (inputEndsWithSeparator(input)) {
            errorMessages.add("Number expected but EOF found");
        }
    }

    private double getSumOfTheValues(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    private String getFormattedErrorMessage(List<String> errorMessages) {
        StringBuilder errorMessage = new StringBuilder();
        for(int i = 0; i < errorMessages.size(); i++) {
            if(i > 0) { errorMessage.append("\\n"); }
            errorMessage.append(errorMessages.get(i));
        }
        return errorMessage.toString();
    }

    private boolean areAnyErrors(List<String> errorMessages) {
        return errorMessages.size() > 0;
    }

    private List<Double> convertToDoubles(String[] input) {
        List<Double> numbers = new ArrayList<>();
        for (String stringValue : input) {
            if (stringValue.isEmpty()) { continue; }
            try {
                numbers.add(Double.parseDouble(stringValue));

            } catch (NumberFormatException e) {
                // Skipped for now
            }
        }
        return numbers;
    }

    private String getNegativeNumbersExceptionMessage(List<Double> values) {
        StringBuilder message = new StringBuilder("Negative not allowed :");
        List<Double> negatives = values.stream().filter(i -> i < 0).collect(Collectors.toList());

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

    private boolean containsNegativeNumbers(List<Double> values) {
        return values.stream().anyMatch(value -> value < 0);
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

    private boolean separatorsNextToEachOther(String input, String separators) {
        return input.contains(separators);
    }

    private double convertToOneDecimalPlace(double sum) {
        return Math.round(sum * 10) / 10.0;
    }

    private boolean isInteger(Double sum) {
        return sum % 1 == 0;
    }

    private boolean isSingleNumber(String input) {
        return input.length() == 1;
    }

    private String[] splitInput(String input, String customSeparator) {
        if (customSeparator == null) {
            customSeparator = "[,\n]";
        } else if (isVerticalSeparator(customSeparator)) {
            customSeparator = escapeVerticalBarRegexSpecialSymbol();
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
