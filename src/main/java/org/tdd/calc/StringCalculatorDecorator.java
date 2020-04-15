package org.tdd.calc;

public abstract class StringCalculatorDecorator implements IStringCalculator {
    private final IStringCalculator stringCalculator;

    public StringCalculatorDecorator(IStringCalculator stringCalculator) {
        this.stringCalculator = stringCalculator;
    }

    public String add(String input) {
        return stringCalculator.add(input);
    }

    public String sub(String input) {
        return stringCalculator.sub(input);
    }
}
