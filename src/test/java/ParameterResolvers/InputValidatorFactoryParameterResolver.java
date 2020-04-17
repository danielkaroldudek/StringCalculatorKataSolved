package ParameterResolvers;

import org.junit.jupiter.api.extension.*;
import org.tdd.calc.validation.IInputValidatorFactory;
import org.tdd.calc.validation.InputValidatorFactory;

public class InputValidatorFactoryParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(IInputValidatorFactory.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new InputValidatorFactory();
    }
}
