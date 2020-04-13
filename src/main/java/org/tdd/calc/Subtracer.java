package org.tdd.calc;

import org.tdd.calc.conversion.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class Subtracer implements Calculator {
    private final Converter converter;

    public Subtracer(Converter converter) {
        this.converter = converter;
    }

    public double calculate(List<Double> values) {
        double result = values.get(0);

        for(Double value : values.stream().skip(1).collect(Collectors.toList())) {
            result -= value;
        }

        return converter.convertToOneDecimalPlace(result);
    }
}
