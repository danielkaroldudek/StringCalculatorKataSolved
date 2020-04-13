package org.tdd.calc.validation.conditions;

import org.tdd.calc.validation.errors.Error;

import java.util.List;
import java.util.Optional;

public interface InputValidationCondition {
    Optional<List<Error>> validate(String input);
}
