package org.tdd.calc.validation;

import java.util.List;
import java.util.regex.Pattern;

public class Verifications {
    public boolean isInteger(Double sum) {
        return sum % 1 == 0;
    }

    public boolean containsNegativeNumbers(List<Double> values) {
        return values.stream().anyMatch(value -> value < 0);
    }

    public boolean hasMultipleSeparators(String customSeparator, String input) {
        return (customSeparator != null) &&
                Pattern.compile("[,]").matcher(input).find();
    }

    public boolean isVerticalSeparator(String customSeparator) {
        return customSeparator.equals("|");
    }
}
