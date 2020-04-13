package org.tdd.calc.manipulation;

import org.apache.commons.lang3.StringUtils;
import org.tdd.calc.Separator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StringManipulator {
    public int indexOf(String input, String value) {
        return input.indexOf(value);
    }

    public List<String> split(String input, String customSeparator) {
        return Arrays.asList(input.split(customSeparator));
    }

    public Separator getSeparator(String input) {
        Optional<String> customSeparator = Optional.ofNullable(StringUtils.substringBetween(input, "//", "\n"));
        return new Separator(customSeparator.orElse("[,\n]"), customSeparator.isPresent());
    }

    public String removeSeparatorFromInput(String input) {
        return input.substring(indexOf(input, "\n") + 1);
    }
}
