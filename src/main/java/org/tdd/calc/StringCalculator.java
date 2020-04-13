package org.tdd.calc;

import org.tdd.calc.conversion.IConverter;
import org.tdd.calc.manipulation.IStringManipulator;
import org.tdd.calc.messaging.IErrorMessages;
import org.tdd.calc.validation.*;
import org.tdd.calc.validation.errors.Error;
import java.util.ArrayList;
import java.util.List;

import static org.tdd.calc.OperationType.ADDING;
import static org.tdd.calc.OperationType.SUBTRACKING;

public class StringCalculator {
    private final IConverter converter;
    private final IErrorMessages errorMessages;
    private final IStringManipulator stringManipulator;

    public StringCalculator(IConverter converter, IErrorMessages errorMessages, IStringManipulator stringManipulator) {
        this.converter = converter;
        this.errorMessages = errorMessages;
        this.stringManipulator = stringManipulator;
    }

    public String add(String input) {
        return prepareForCalculation(input, ADDING);
    }

    public String sub(String input) {
        return prepareForCalculation(input, SUBTRACKING);
    }

    private String prepareForCalculation(String input, OperationType operationType) {
        Separator separator = stringManipulator.getSeparator(input);
        if (separator.isCustom()) input = stringManipulator.removeSeparatorFromInput(input);
        IValidator inputValidator = new InputValidator(input, separator, errorMessages, stringManipulator);

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

        double result = calculate(converter.convertToDoubles(stringManipulator.split(input, separator.get())), operationType);
        return isInteger(result)
                ? String.valueOf((int)result)
                : String.valueOf(result);
    }

    private double calculate(List<Double> values, OperationType operationType) {
        Calculator calculator;

        switch (operationType) {
            default:
            case ADDING:
                calculator = new Adder(converter);
                break;
            case SUBTRACKING:
                calculator = new Subtracer(converter);
                break;
        }

        return calculator.calculate(values);
    }

    private boolean isInteger(Double sum) {
        return sum % 1 == 0;
    }
}
