package org.tdd.calc;

import org.javatuples.Pair;
import org.tdd.calc.conversion.Converter;
import org.tdd.calc.manipulation.InputManipulator;
import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.*;
import org.tdd.calc.validation.Error;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
    private final Converter converter;
    private final ErrorMessages errorMessages;

    public StringCalculator() {
        converter = new Converter();
        errorMessages = new ErrorMessages();
    }

    public String add(String input) {
        InputManipulator inputManipulator = new InputManipulator(input);
        Separator separator = inputManipulator.getSeparator();

        if (separator.isCustom()) {
            input = inputManipulator.removeSeparatorFromInput();
        }

        Validator validator = new InputValidator(input, separator, errorMessages, inputManipulator);
        Pair<Boolean, List<Error>> validatedInput = validator.isValid();

        if (!validatedInput.getValue0()) {
            List<String> errorMessages = new ArrayList<>();

            for (Error error : validatedInput.getValue1()) {
                switch (error.getErrorType()) {
                    case EMPTY_INPUT:
                        return "0";
                    case SINGLE_NUMBER:
                        return input;
                    default:
                        errorMessages.add(error.getMassage());
                        break;
                }
            }

            return this.errorMessages.getFormattedMessage(errorMessages);
        }

        double result = add(converter.convertToDoubles(inputManipulator.splitInput(separator.get())));
        return isInteger(result)
                ? String.valueOf((int)result)
                : String.valueOf(result);
    }

    private double add(List<Double> values) {
        return converter.convertToOneDecimalPlace(values.stream().mapToDouble(Double::doubleValue).sum());
    }

    public boolean isInteger(Double sum) {
        return sum % 1 == 0;
    }
}
