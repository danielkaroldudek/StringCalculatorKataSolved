package org.tdd.calc.manipulation;

import org.tdd.calc.Separator;

import java.util.List;

public interface IStringManipulator {
    int indexOf(String input, String value);
    List<String> split(String input, String customSeparator);
    Separator getSeparator(String input);
    String removeSeparatorFromInput(String input);
}
