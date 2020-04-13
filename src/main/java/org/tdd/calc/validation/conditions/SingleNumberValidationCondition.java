package org.tdd.calc.validation.conditions;

import org.tdd.calc.messaging.IErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.List;
import java.util.Optional;

public class SingleNumberValidationCondition implements InputValidationCondition {
    private final IErrorMessages errorMessages;

    public SingleNumberValidationCondition(IErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        return input.length() == 1
            ? Optional.of(List.of(new Error(ErrorTypes.SINGLE_NUMBER, errorMessages.getSingleNumber())))
            : Optional.empty();
    }
}
