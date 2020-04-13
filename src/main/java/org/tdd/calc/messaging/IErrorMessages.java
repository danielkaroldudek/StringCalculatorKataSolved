package org.tdd.calc.messaging;

import java.util.List;

public interface IErrorMessages {
    String getFormattedMessage(List<String> messages);
    String getNegativeNumbersExceptionMessage(List<String> values);
    String getTwoSeparatorsException(String firstSeparator, String secondSeparator, int indexOfSecondSeparator);
    String getNumberExpectedException(String separator, int indexOfOccurrence);
    String getEmptyInput();
    String getSingleNumber();
    String getEndsWithSeparator();
}
