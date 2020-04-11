package org.tdd.calc.messaging;

import org.tdd.calc.manipulation.StringManipulation;
import org.tdd.calc.validation.Verifications;

import java.util.List;
import java.util.stream.Collectors;

public class Messages {
    private final Verifications verifications;
    private final StringManipulation stringManipulation;

    public Messages() {
        verifications = new Verifications();
        stringManipulation = new StringManipulation();
    }

    public String getFormattedErrorMessage(List<String> errorMessages) {
        StringBuilder errorMessage = new StringBuilder();
        for(int i = 0; i < errorMessages.size(); i++) {
            if(i > 0) { errorMessage.append("\\n"); }
            errorMessage.append(errorMessages.get(i));
        }
        return errorMessage.toString();
    }

    public String getNegativeNumbersExceptionMessage(List<Double> values) {
        StringBuilder message = new StringBuilder("Negative not allowed :");
        List<Double> negatives = values.stream().filter(i -> i < 0).collect(Collectors.toList());

        for(int i = 0; i < negatives.size(); i++) {
            double value = negatives.toArray(new Double[0])[i];
            if (i > 0) {
                message.append(",");
            }
            if (verifications.isInteger(value)) {
                message.append(" ");
                message.append((int)value);
                continue;
            }
            message.append(" ");
            message.append(value);
        }

        return message.toString();
    }

    public String getTwoSeparatorsException(String customSeparator, String input) {
        return "'" + customSeparator +
                "' expected but ',' found at position " +
                stringManipulation.indexOfCommaSeparator(input);
    }

    public String getNumberExpectedException(String separator, String input) {
        int index = separator.equals(",")
                ? stringManipulation.indexOfCommaSeparator(input)
                : stringManipulation.indexOfNewLineSeparator(input);
        return "Number expected but '" +
                separator +
                "' found at position " +
                index;
    }

    public String getEmptyInput() {
        return "The input was empty";
    }

    public String getSingleNumber() {
        return "Input was a single number";
    }

    public String getEndsWithSeparator() {
        return "Number expected but EOF found";
    }
}
