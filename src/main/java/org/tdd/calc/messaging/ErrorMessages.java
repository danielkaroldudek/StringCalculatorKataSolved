package org.tdd.calc.messaging;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorMessages extends Messages implements IErrorMessages {
    public String getNegativeNumbersExceptionMessage(List<String> values) {
        String negatives = values.stream().filter(i -> i.startsWith("-")).collect(Collectors.joining(", "));
        return String.format("Negative not allowed : %s", negatives);
    }

    public String getTwoSeparatorsException(String firstSeparator, String secondSeparator, int indexOfSecondSeparator) {
        return String.format("'%s' expected but '%s' found at position %s", firstSeparator, secondSeparator, indexOfSecondSeparator);
    }

    public String getNumberExpectedException(String separator, int indexOfOccurrence) {
        return String.format("Number expected but '%s' found at position %s", separator, indexOfOccurrence);
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
