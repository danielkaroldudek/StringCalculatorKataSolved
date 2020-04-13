package org.tdd.calc.validation.conditions;

import org.tdd.calc.messaging.IErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.List;
import java.util.Optional;

public class EmptyInputValidationCondition implements InputValidationCondition {
    private final IErrorMessages errorMessages;

    public EmptyInputValidationCondition(IErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        return input.isEmpty()
                ? Optional.of(List.of(new Error(ErrorTypes.EMPTY_INPUT, errorMessages.getEmptyInput())))
                : Optional.empty();
    }
}
