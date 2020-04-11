package org.tdd.calc.messaging;

import org.tdd.calc.manipulation.InputManipulation;

import java.util.List;
import java.util.stream.Collectors;

public class Messages {
    private final InputManipulation inputManipulation;

    public Messages(InputManipulation inputManipulation) {
        this.inputManipulation = inputManipulation;
    }

    public String getFormattedErrorMessage(List<String> errorMessages) {
        StringBuilder errorMessage = new StringBuilder();
        for(int i = 0; i < errorMessages.size(); i++) {
            if(i > 0) { errorMessage.append("\\n"); }
            errorMessage.append(errorMessages.get(i));
        }
        return errorMessage.toString();
    }

    public String getNegativeNumbersExceptionMessage(List<String> values) {
        StringBuilder message = new StringBuilder("Negative not allowed :");
        List<String> negatives = values.stream().filter(i -> i.startsWith("-")).collect(Collectors.toList());

        int iterator = 0;
        for(String value : negatives) {
            if (iterator > 0) {
                message.append(",");
            }
            message.append(" ");
            message.append(value);
            iterator++;
        }

        return message.toString();
    }

    public String getTwoSeparatorsException(String customSeparator) {
        return "'" + customSeparator +
                "' expected but ',' found at position " +
                inputManipulation.indexOf(",");
    }

    public String getNumberExpectedException(String separator) {
        int index = separator.equals(",")
                ? inputManipulation.indexOf(",")
                : inputManipulation.indexOf("\n");
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
