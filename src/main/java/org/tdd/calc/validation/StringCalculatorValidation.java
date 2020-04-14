package org.tdd.calc.validation;

import org.tdd.calc.IStringCalculator;
import org.tdd.calc.Separator;
import org.tdd.calc.StringCalculatorDecorator;
import org.tdd.calc.manipulation.IStringManipulator;
import org.tdd.calc.messaging.IErrorMessages;
import org.tdd.calc.validation.errors.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringCalculatorValidation extends StringCalculatorDecorator {
    private final IStringManipulator stringManipulator;
    private final IErrorMessages errorMessages;

    public StringCalculatorValidation(IStringCalculator stringCalculator, IStringManipulator stringManipulator, IErrorMessages errorMessages) {
        super(stringCalculator);
        this.stringManipulator = stringManipulator;
        this.errorMessages = errorMessages;
    }

    public String add(String input) {
        return getErrorMessage(input).orElseGet(() -> super.add(input));
    }

    public String sub(String input) {
        return getErrorMessage(input).orElseGet(() -> super.sub(input));
    }

    private Optional<String> getErrorMessage(String input) {
        Separator separator = stringManipulator.getSeparator(input);
        if (separator.isCustom()) input = stringManipulator.removeSeparatorFromInput(input);
        IValidator inputValidator = new InputValidator(input, separator, errorMessages, stringManipulator);

        if (!inputValidator.isValid()) {
            List<Error> errors = new ArrayList<>();

            for (Error error : inputValidator.getErrors()) {
                switch (error.getErrorType()) {
                    case EMPTY_INPUT:
                        return Optional.of("0");
                    case SINGLE_NUMBER:
                        return Optional.of(input);
                    default:
                        errors.add(error);
                        break;
                }
            }

            return Optional.of(errorMessages.getFormattedMessage(errors));
        }

        return Optional.empty();
    }
}
