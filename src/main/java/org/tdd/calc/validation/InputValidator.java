package org.tdd.calc.validation;

import org.javatuples.Pair;
import org.tdd.calc.messaging.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InputValidator implements Validator {
    private final String input;
    private final Messages messages;

    private List<Error> errors;

    public InputValidator(String input, Messages messages) {
        this.input = input;
        this.messages = messages;

        errors = new ArrayList<>();
    }

    public Pair<Boolean, List<Error>> isValid() {
        Boolean isValid = !isEmpty()
                && !isSingleNumber()
                && !separatorsNextToEachOther()
                && !endsWithSeparator();

        return new Pair<>(isValid, errors);
    }

    private boolean isEmpty() {
        if(input.isEmpty()) {
            errors.add(new Error(ErrorTypes.EMPTY_INPUT, messages.getEmptyInput()));
            return true;
        }
        return false;
    }

    private boolean isSingleNumber() {
        if (input.length() == 1) {
            errors.add(new Error(ErrorTypes.SINGLE_NUMBER, messages.getSingleNumber()));
            return true;
        }
        return false;
    }

    private boolean separatorsNextToEachOther() {
        boolean validationError = false;

        String[][] possibleSeparatorNextToEachOther = {
                {",\n", "\\n"}, {"\n\n", "\\n"}, {"\n,", ","}, {",,", ","}
        };

        for (String[] separator : possibleSeparatorNextToEachOther) {
            if (input.contains(separator[0])) {
                errors.add(new Error(ErrorTypes.NUMBER_EXPECTED, messages.getNumberExpectedException(separator[1], input)));
                validationError = true;
            }
        }

        return validationError;
    }

    private boolean endsWithSeparator() {
        if (Pattern.compile("[,\n]$").matcher(input).find()) {
            errors.add(new Error(ErrorTypes.ENDS_WITH_SEPARATOR, messages.getEndsWithSeparator()));
            return true;
        }
        return false;
    }
}
