package org.tdd.calc.conversion;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Converter implements IConverter  {
    public List<Double> convertToDoubles(List<String> input) {
        Predicate<String> isDoublePredicate = value -> Pattern.compile("[0-9]|[0-9].[0-9]").matcher(value).find();
        return input.stream()
                .filter(isDoublePredicate)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    public double convertToOneDecimalPlace(double sum) {
        return Math.round(sum * 10) / 10.0;
    }
}
