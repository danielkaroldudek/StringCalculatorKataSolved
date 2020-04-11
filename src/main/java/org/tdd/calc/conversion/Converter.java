package org.tdd.calc.conversion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Converter {
    public List<Double> convertToDoubles(String[] input) {
        List<Double> numbers = new ArrayList<>();
        for (String stringValue : input) {
            if (Pattern.compile("[0-9]|[0-9].[0-9]").matcher(stringValue).find()) {
                numbers.add(Double.parseDouble(stringValue));
            }
        }
        return numbers;
    }

    public double convertToOneDecimalPlace(double sum) {
        return Math.round(sum * 10) / 10.0;
    }
}
