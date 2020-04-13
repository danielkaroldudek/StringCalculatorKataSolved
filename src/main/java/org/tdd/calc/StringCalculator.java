package org.tdd.calc;

import org.tdd.calc.conversion.Converter;
import org.tdd.calc.manipulation.StringManipulator;
import org.tdd.calc.messaging.ErrorMessages;
import org.tdd.calc.validation.*;
import org.tdd.calc.validation.errors.Error;
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
        StringManipulator stringManipulator = new StringManipulator();
        Separator separator = stringManipulator.getSeparator(input);
        if (separator.isCustom()) input = stringManipulator.removeSeparatorFromInput(input);
        Validator inputValidator = new InputValidator(input, separator, errorMessages, stringManipulator);

        if (!inputValidator.isValid()) {
            List<String> errorMessages = new ArrayList<>();

            for (Error error : inputValidator.getErrors()) {
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

        double result = add(converter.convertToDoubles(stringManipulator.split(input, separator.get())));
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
