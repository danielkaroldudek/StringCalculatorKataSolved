package org.tdd.calc.messaging;

import org.tdd.calc.validation.errors.Error;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Messages {
    public String getFormattedMessage(List<Error> errors) {
        return errors.stream().map(Error::getMassage).collect(Collectors.joining( "\\n" ));
    }
}
