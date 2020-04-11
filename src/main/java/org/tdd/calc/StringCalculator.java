package org.tdd.calc;

import org.javatuples.Pair;
import org.tdd.calc.conversion.Converter;
import org.tdd.calc.manipulation.InputManipulation;
import org.tdd.calc.messaging.Messages;
import org.tdd.calc.validation.*;
import org.tdd.calc.validation.Error;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
    private final Converter converter;

    public StringCalculator() {
        converter = new Converter();
    }

    public String add(String input) {
        InputManipulation inputManipulation = new InputManipulation(input);
        Separator separator = inputManipulation.getSeparator();
        if (separator.isCustom()) {
            input = inputManipulation.removeSeparatorFromInput();
        }

        Messages messages = new Messages(inputManipulation);
        Validator validator = new InputValidator(input, separator, messages, inputManipulation);
        Pair<Boolean, List<Error>> validatedInput = validator.isValid();

        List<Double> values;
        List<String> errorMessages = new ArrayList<>();

        if (!validatedInput.getValue0()) {
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
        }

        values = converter.convertToDoubles(inputManipulation.splitInput(separator.getValue()));

        if (!validatedInput.getValue0()) {
            return messages.getFormattedErrorMessage(errorMessages);
        }

        double result = add(values);
        if (isInteger(result)) {
            return String.valueOf((int)result);
        }

        return String.valueOf(converter.convertToOneDecimalPlace(result));
    }

    private double add(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    public boolean isInteger(Double sum) {
        return sum % 1 == 0;
    }
}
