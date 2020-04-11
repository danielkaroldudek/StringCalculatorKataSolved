package org.tdd.calc.conversion;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public List<Double> convertToDoubles(String[] input) {
        List<Double> numbers = new ArrayList<>();
        for (String stringValue : input) {
            if (stringValue.isEmpty()) { continue; }
            try {
                numbers.add(Double.parseDouble(stringValue));

            } catch (NumberFormatException e) {
                // Skipped for now
            }
        }
        return numbers;
    }

    public double convertToOneDecimalPlace(double sum) {
        return Math.round(sum * 10) / 10.0;
    }
}
