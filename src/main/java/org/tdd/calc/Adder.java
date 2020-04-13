package org.tdd.calc;

import org.tdd.calc.conversion.IConverter;

import java.util.List;

public class Adder implements Calculator {
    private final IConverter converter;

    public Adder(IConverter converter) {
        this.converter = converter;
    }

    public double calculate(List<Double> values) {
        return converter.convertToOneDecimalPlace(values.stream().mapToDouble(Double::doubleValue).sum());
    }
}
