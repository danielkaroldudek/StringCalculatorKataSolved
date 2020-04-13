package org.tdd.calc.validation.conditions;

import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.StringManipulator;
import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NegativeNumbersCondition implements InputValidationCondition {
    private final Separator separator;
    private final StringManipulator stringManipulator;
    private final ErrorMessages errorMessages;

    public NegativeNumbersCondition(Separator separator, StringManipulator stringManipulator, ErrorMessages errorMessages) {
        this.stringManipulator = stringManipulator;
        this.separator = separator;
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        List<Error> errors = new ArrayList<>();
        List<String> negativeValues = stringManipulator.split(input, separator.get()).stream()
                .filter(value -> value.startsWith("-"))
                .collect(Collectors.toList());
        if(negativeValues.size() > 0) {
            errors.add(new Error(ErrorTypes.NEGATIVE_NUMBERS,
                    errorMessages.getNegativeNumbersExceptionMessage(negativeValues)));
        }
        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }
}
