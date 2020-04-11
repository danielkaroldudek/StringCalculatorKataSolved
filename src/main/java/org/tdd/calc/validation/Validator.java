package org.tdd.calc.validation;

import org.javatuples.Pair;

import java.util.List;

public interface Validator {
    Pair<Boolean, List<Error>> isValid();
}
