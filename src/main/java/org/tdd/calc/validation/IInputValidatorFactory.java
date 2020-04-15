package org.tdd.calc.validation;

import org.tdd.calc.Separator;
import org.tdd.calc.manipulation.IStringManipulator;
import org.tdd.calc.messaging.IErrorMessages;

public interface IInputValidatorFactory {
    IValidator create(String input, Separator separator, IErrorMessages errorMessages, IStringManipulator stringManipulator);
}
