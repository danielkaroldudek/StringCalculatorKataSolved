package org.tdd.calc;

import org.tdd.calc.conversion.IConverter;
import org.tdd.calc.manipulation.IStringManipulator;
import java.util.List;

import static org.tdd.calc.OperationType.ADDING;
import static org.tdd.calc.OperationType.SUBTRACKING;

public class StringCalculator implements IStringCalculator {
    private final IConverter converter;
    private final IStringManipulator stringManipulator;

    public StringCalculator(IConverter converter, IStringManipulator stringManipulator) {
        this.converter = converter;
        this.stringManipulator = stringManipulator;
    }

    public String add(String input) {
        return getCalculation(input, ADDING);
    }

    public String sub(String input) {
        return getCalculation(input, SUBTRACKING);
    }

    private String getCalculation(String input, OperationType operationType) {
        Separator separator = stringManipulator.getSeparator(input);
        if (separator.isCustom()) input = stringManipulator.removeSeparatorFromInput(input);

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
