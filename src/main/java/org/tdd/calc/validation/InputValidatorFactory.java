package org.tdd.calc.validation;

import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.IStringManipulator;
import org.tdd.calc.messaging.IErrorMessages;

public class InputValidatorFactory implements IInputValidatorFactory {
    @Override
    public IValidator create(String input, Separator separator, IErrorMessages errorMessages, IStringManipulator stringManipulator) {
        return new InputValidator(input, separator, errorMessages, stringManipulator);
    }
}
