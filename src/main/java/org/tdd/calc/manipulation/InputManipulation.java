package org.tdd.calc.manipulation;

import org.apache.commons.lang3.StringUtils;
import org.tdd.calc.Separator;

public class InputManipulation {
    private String input;

    public InputManipulation (String input) {
        this.input = input;
    }

    public int indexOf(String separator) {
        return input.indexOf(separator);
    }

    public String[] splitInput(String customSeparator) {
        return input.split(customSeparator);
    }

    public Separator getSeparator() {
        Separator separator = new Separator();
        String customSeparator = StringUtils.substringBetween(input, "//", "\n");
        if (customSeparator == null) {
            separator.setValue("[,\n]");
        } else {
            separator.setValue(customSeparator);
            separator.setCustom();
        }

        return separator;
    }

    public String removeSeparatorFromInput() {
        input = input.substring(input.indexOf("\n") + 1);
        return input;
    }
}
