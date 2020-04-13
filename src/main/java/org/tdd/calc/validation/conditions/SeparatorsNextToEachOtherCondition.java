package org.tdd.calc.validation.conditions;

import org.javatuples.Triplet;
import org.tdd.calc.manipulation.StringManipulator;
import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.errors.Error;
import org.tdd.calc.validation.errors.ErrorTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeparatorsNextToEachOtherCondition implements InputValidationCondition {
    private final StringManipulator stringManipulator;
    private final ErrorMessages errorMessages;

    public SeparatorsNextToEachOtherCondition(StringManipulator stringManipulator, ErrorMessages errorMessages) {
        this.stringManipulator = stringManipulator;
        this.errorMessages = errorMessages;
    }

    public Optional<List<Error>> validate(String input) {
        List<Error> errors = new ArrayList<>();

        List<Triplet<String, String, String>> possibleSeparatorNextToEachOther =
                List.of(new Triplet<>(",\n", "\n", "\\n"),
                        new Triplet<>("\n\n", "\n", "\\n"),
                        new Triplet<>("\n,", ",", ","),
                        new Triplet<>(",,", ",", ",")
                );

        possibleSeparatorNextToEachOther.forEach(separator -> {
            if (input.contains(separator.getValue0())) {
                errors.add(new Error(ErrorTypes.NUMBER_EXPECTED,
                        errorMessages.getNumberExpectedException(separator.getValue2(), stringManipulator.indexOf(input, separator.getValue1()))));
            }
        });

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }
}
