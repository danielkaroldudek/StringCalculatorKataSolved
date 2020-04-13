package org.tdd.calc.validation.conditions;

import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.StringManipulator;
import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class MultipleSeparatorsCondition implements InputValidationCondition {
    private final Separator separator;
    private final StringManipulator stringManipulator;
    private final ErrorMessages errorMessages;

    public MultipleSeparatorsCondition(Separator separator, StringManipulator stringManipulator, ErrorMessages errorMessages) {
        this.separator = separator;
        this.stringManipulator = stringManipulator;
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        List<Error> errors = new ArrayList<>();

        List<String> defaultSeparators = List.of(",", "\n");
        defaultSeparators.stream()
                .filter(defaultSeparator ->
                        separator.isCustom() && Pattern.compile(defaultSeparator).matcher(input).find())
                .forEach(defaultSeparator ->
                        errors.add(new Error(ErrorTypes.MULTIPLE_SEPARATORS,
                                errorMessages.getTwoSeparatorsException(separator.get(), defaultSeparator, stringManipulator.indexOf(input, defaultSeparator)))));

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }
}
