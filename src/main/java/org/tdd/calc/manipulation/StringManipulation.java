package org.tdd.calc.manipulation;

import org.apache.commons.lang3.StringUtils;
import org.tdd.calc.validation.Verifications;

public class StringManipulation {
    private final Verifications verifications;

    public StringManipulation() {
        verifications = new Verifications();
    }

    public int indexOfCommaSeparator(String input) {
        return input.indexOf(',');
    }

    public int indexOfNewLineSeparator(String input) {
        return input.indexOf("\n");
    }

    public String getSubstringIfCustomSeparatorPresent(String  input, String customSeparator) {
        if(customSeparator != null) {
            input = input.substring(input.indexOf("\n") + 1);
        }
        return input;
    }

    public String getCustomSeparator(String input) {
        return StringUtils.substringBetween(input, "//", "\n");
    }

    public String[] splitInput(String input, String customSeparator) {
        if (customSeparator == null) {
            customSeparator = "[,\n]";
        } else if (verifications.isVerticalSeparator(customSeparator)) {
            customSeparator = escapeVerticalBarRegexSpecialSymbol();
        }

        return input.split(customSeparator);
    }

    private String escapeVerticalBarRegexSpecialSymbol() {
        return "\\|";
    }
}
