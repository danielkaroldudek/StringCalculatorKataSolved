package org.tdd.calc.manipulation;

import org.apache.commons.lang3.StringUtils;
import org.tdd.calc.Separator;

public class InputManipulator {
    private String input;

    public InputManipulator(String input) {
        this.input = input;
    }

    public int indexOf(String separator) {
        return input.indexOf(separator);
    }

    public String[] splitInput(String customSeparator) {
        return input.split(customSeparator);
    }

    public Separator getSeparator() {
        String customSeparator = StringUtils.substringBetween(input, "//", "\n");
        return customSeparator == null
                ? new Separator("[,\n]", false)
                : new Separator(customSeparator, true);
    }

    public String removeSeparatorFromInput() {
        input = input.substring(input.indexOf("\n") + 1);
        return input;
    }
}
