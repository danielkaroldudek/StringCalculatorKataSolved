package org.tdd.calc.messaging;

import java.util.List;

public abstract class Messages {
    public String getFormattedMessage(List<String> messages) {
        return String.join("\\n", messages);
    }
}
