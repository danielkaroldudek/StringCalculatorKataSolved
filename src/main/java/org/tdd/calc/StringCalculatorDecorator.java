package org.tdd.calc;

public abstract class StringCalculatorDecorator implements IStringCalculator {
    protected final IStringCalculator stringCalculator;

    protected StringCalculatorDecorator(IStringCalculator stringCalculator) {
        this.stringCalculator = stringCalculator;
    }

    public String add(String input) {
        return stringCalculator.add(input);
    }

    public String sub(String input) {
        return stringCalculator.sub(input);
    }
}
