package org.tdd.calc.messaging;

import org.tdd.calc.validation.errors.Error;

import java.util.List;

public interface IErrorMessages {
    String getFormattedMessage(List<Error> messages);
    String getNegativeNumbersExceptionMessage(List<String> values);
    String getTwoSeparatorsException(String firstSeparator, String secondSeparator, int indexOfSecondSeparator);
    String getNumberExpectedException(String separator, int indexOfOccurrence);
    String getEmptyInput();
    String getSingleNumber();
    String getEndsWithSeparator();
}
