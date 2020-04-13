package org.tdd.calc.validation.conditions;

import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class EndsWithSeparatorCondition implements InputValidationCondition {
    private final ErrorMessages errorMessages;

    public EndsWithSeparatorCondition(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        if (Pattern.compile("[,\n]$").matcher(input).find()) {
            return Optional.of(List.of(new Error(ErrorTypes.ENDS_WITH_SEPARATOR, errorMessages.getEndsWithSeparator())));
        }
        return Optional.empty();
    }
}
