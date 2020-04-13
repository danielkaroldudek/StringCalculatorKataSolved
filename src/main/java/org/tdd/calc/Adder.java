package org.tdd.calc;

import org.tdd.calc.conversion.Converter;

import java.util.List;

public class Adder implements Calculator {
    private final Converter converter;

    public Adder(Converter converter) {
        this.converter = converter;
    }

    public double calculate(List<Double> values) {
        return converter.convertToOneDecimalPlace(values.stream().mapToDouble(Double::doubleValue).sum());
    }
}
