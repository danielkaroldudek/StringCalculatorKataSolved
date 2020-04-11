package org.tdd.calc.validation;

import org.javatuples.Pair;
import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.InputManipulation;
import org.tdd.calc.messaging.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputValidator implements Validator {
    private final String input;
    private final Messages messages;
    private final Separator separator;
    private final InputManipulation inputManipulation;

    private List<Error> errors;

    public InputValidator(String input, Separator separator, Messages messages, InputManipulation inputManipulation) {
        this.input = input;
        this.messages = messages;
        this.separator = separator;
        this.inputManipulation = inputManipulation;

        errors = new ArrayList<>();
    }

    public Pair<Boolean, List<Error>> isValid() {
        isEmpty();
        isSingleNumber();
        separatorsNextToEachOther();
        endsWithSeparator();
        hasMultipleSeparators();
        containsNegativeNumbers();

        return new Pair<>(errors.isEmpty(), errors);
    }

    private void isEmpty() {
        if(input.isEmpty()) {
            errors.add(new Error(ErrorTypes.EMPTY_INPUT, messages.getEmptyInput()));
        }
    }

    private void containsNegativeNumbers() {
        List<String> negativeValues = Arrays.stream(inputManipulation.splitInput(separator.getValue())).filter(value -> value.startsWith("-")).collect(Collectors.toList());
        if(negativeValues.size() > 0) {
            errors.add(new Error(ErrorTypes.NEGATIVE_NUMBERS,
                    messages.getNegativeNumbersExceptionMessage(negativeValues)));
        }
    }

    private void hasMultipleSeparators() {
        if(separator.isCustom() && Pattern.compile("[,\n]").matcher(input).find()) {
            errors.add(new Error(ErrorTypes.MULTIPLE_SEPARATORS,
                    messages.getTwoSeparatorsException(separator.getValue())));
        }
    }

    private void isSingleNumber() {
        if (input.length() == 1) {
            errors.add(new Error(ErrorTypes.SINGLE_NUMBER, messages.getSingleNumber()));
        }
    }

    private void separatorsNextToEachOther() {
        List<Pair<String, String>> possibleSeparatorNextToEachOther = Arrays.asList(
          new Pair<>(",\n", "\\n"), new Pair<>("\n\n", "\\n"), new Pair<>("\n,", ","), new Pair<>(",,", ",")
        );

        possibleSeparatorNextToEachOther.forEach(separator -> {
            if (input.contains(separator.getValue0())) {
                errors.add(new Error(ErrorTypes.NUMBER_EXPECTED, messages.getNumberExpectedException(separator.getValue1())));
            }
        });
    }

    private void endsWithSeparator() {
        if (Pattern.compile("[,\n]$").matcher(input).find()) {
            errors.add(new Error(ErrorTypes.ENDS_WITH_SEPARATOR, messages.getEndsWithSeparator()));
        }
    }
}
