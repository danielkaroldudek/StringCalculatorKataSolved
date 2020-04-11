package org.tdd.calc.validation;

public class Error {
    private final ErrorTypes errorType;
    private final String massage;

    public Error(ErrorTypes errorType, String massage) {
        this.errorType = errorType;
        this.massage = massage;
    }

    public ErrorTypes getErrorType() {
        return errorType;
    }

    public String getMassage() {
        return massage;
    }
}
