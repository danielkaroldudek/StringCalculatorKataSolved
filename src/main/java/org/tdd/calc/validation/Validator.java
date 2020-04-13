package org.tdd.calc.validation;

import org.tdd.calc.validation.errors.Error;

import java.util.List;

public interface Validator {
    boolean isValid();
    List<Error> getErrors();
}
