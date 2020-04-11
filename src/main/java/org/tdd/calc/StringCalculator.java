package org.tdd.calc;

import org.javatuples.Pair;
import org.tdd.calc.conversion.Converter;
import org.tdd.calc.manipulation.StringManipulation;
import org.tdd.calc.messaging.Messages;
import org.tdd.calc.validation.*;
import org.tdd.calc.validation.Error;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
    private final Messages messages;
    private final Verifications verifications;
    private final StringManipulation stringManipulation;
    private final Converter converter;

    public StringCalculator() {
        messages = new Messages();
        verifications = new Verifications();
        stringManipulation = new StringManipulation();
        converter = new Converter();
    }

    public String add(String input) {
        Validator validator = new InputValidator(input, messages);
        Pair<Boolean, List<Error>> validatedInput = validator.isValid();

        List<Double> values;
        String customSeparator;
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

        customSeparator = stringManipulation.getCustomSeparator(input);
        input = stringManipulation.getSubstringIfCustomSeparatorPresent(input, customSeparator);
        if (verifications.hasMultipleSeparators(customSeparator, input)) {
            errorMessages.add(messages.getTwoSeparatorsException(customSeparator, input));
        }

        values = converter.convertToDoubles(stringManipulation.splitInput(input, customSeparator));
        if(verifications.containsNegativeNumbers(values)) {
            errorMessages.add(messages.getNegativeNumbersExceptionMessage(values));
        }

        if (errorMessages.size() > 0) {
            return messages.getFormattedErrorMessage(errorMessages);
        }

        double result = add(values);
        if (verifications.isInteger(result)) {
            return String.valueOf((int)result);
        }

        return String.valueOf(converter.convertToOneDecimalPlace(result));
    }

    private double add(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }
}
