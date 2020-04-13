package org.tdd.calc.conversion;

import java.util.List;

public interface IConverter {
    List<Double> convertToDoubles(List<String> input);
    double convertToOneDecimalPlace(double sum);
}
