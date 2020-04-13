package org.tdd.calc.validation;

import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.StringManipulator;
import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.conditions.*;
import org.tdd.calc.validation.errors.Error;

import java.util.ArrayList;
import java.util.List;

public class InputValidator implements Validator {
    private final String input;
    private final Separator separator;
    private final ErrorMessages errorMessages;
    private final StringManipulator stringManipulator;
    private final List<Error> errors;

    public InputValidator(String input, Separator separator, ErrorMessages errorMessages, StringManipulator stringManipulator) {
        this.input = input;
        this.separator = separator;
        this.errorMessages = errorMessages;
        this.stringManipulator = stringManipulator;

        errors = new ArrayList<>();
    }

    public boolean isValid() {
        final List<InputValidationCondition> conditions = new ArrayList<>();

        conditions.add(new EmptyInputValidationCondition(errorMessages));
        conditions.add(new SingleNumberValidationCondition(errorMessages));
        conditions.add(new EndsWithSeparatorCondition(errorMessages));
        conditions.add(new SeparatorsNextToEachOtherCondition(stringManipulator, errorMessages));
        conditions.add(new MultipleSeparatorsCondition(separator, stringManipulator, errorMessages));
        conditions.add(new NegativeNumbersCondition(separator, stringManipulator, errorMessages));

        conditions.forEach(condition -> condition.validate(input).ifPresent(errors::addAll));

        return errors.isEmpty();
    }

    public List<Error> getErrors() {
        return errors;
    }
}
