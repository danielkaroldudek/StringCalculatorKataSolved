package org.tdd.calc.validation.conditions;

import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.List;
import java.util.Optional;

public class EmptyInputValidationCondition implements InputValidationCondition {
    private final ErrorMessages errorMessages;

    public EmptyInputValidationCondition(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        return input.isEmpty()
                ? Optional.of(List.of(new Error(ErrorTypes.EMPTY_INPUT, errorMessages.getEmptyInput())))
                : Optional.empty();
    }
}
