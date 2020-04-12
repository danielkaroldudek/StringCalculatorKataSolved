package org.tdd.calc.validation;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.InputManipulator;
import org.tdd.calc.messaging.ErrorMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputValidator implements Validator {
    private final String input;
    private final ErrorMessages errorMessages;
    private final Separator separator;
    private final InputManipulator inputManipulator;

    private List<Error> errors;

    public InputValidator(String input, Separator separator, ErrorMessages errorMessages, InputManipulator inputManipulator) {
        this.input = input;
        this.errorMessages = errorMessages;
        this.separator = separator;
        this.inputManipulator = inputManipulator;

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
            errors.add(new Error(ErrorTypes.EMPTY_INPUT, errorMessages.getEmptyInput()));
        }
    }

    private void containsNegativeNumbers() {
        List<String> negativeValues = Arrays.stream(inputManipulator.splitInput(separator.get())).filter(value -> value.startsWith("-")).collect(Collectors.toList());
        if(negativeValues.size() > 0) {
            errors.add(new Error(ErrorTypes.NEGATIVE_NUMBERS,
                    errorMessages.getNegativeNumbersExceptionMessage(negativeValues)));
        }
    }

    private void hasMultipleSeparators() {
        List<String> defaultSeparators = Arrays.asList(",", "\n");
        defaultSeparators.forEach(defaultSeparator -> {
            if(separator.isCustom() && Pattern.compile(defaultSeparator).matcher(input).find()) {
                errors.add(new Error(ErrorTypes.MULTIPLE_SEPARATORS,
                        errorMessages.getTwoSeparatorsException(separator.get(), defaultSeparator, inputManipulator.indexOf(defaultSeparator))));
            }
        });
    }

    private void isSingleNumber() {
        if (input.length() == 1) {
            errors.add(new Error(ErrorTypes.SINGLE_NUMBER, errorMessages.getSingleNumber()));
        }
    }

    private void separatorsNextToEachOther() {
        List<Triplet<String, String, String>> possibleSeparatorNextToEachOther = Arrays.asList(
          new Triplet<>(",\n", "\n", "\\n"), new Triplet<>("\n\n", "\n", "\\n"), new Triplet<>("\n,", ",", ","), new Triplet<>(",,", ",", ",")
        );

        possibleSeparatorNextToEachOther.forEach(separator -> {
            if (input.contains(separator.getValue0())) {
                errors.add(new Error(ErrorTypes.NUMBER_EXPECTED,
                        errorMessages.getNumberExpectedException(separator.getValue2(), inputManipulator.indexOf(separator.getValue1()))));
            }
        });
    }

    private void endsWithSeparator() {
        if (Pattern.compile("[,\n]$").matcher(input).find()) {
            errors.add(new Error(ErrorTypes.ENDS_WITH_SEPARATOR, errorMessages.getEndsWithSeparator()));
        }
    }
}
