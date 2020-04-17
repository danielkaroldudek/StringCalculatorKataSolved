package ParameterResolvers;

import org.junit.jupiter.api.extension.*;
import org.tdd.calc.manipulation.IStringManipulator;
import org.tdd.calc.manipulation.StringManipulator;

public class StringManipulatorParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(IStringManipulator.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new StringManipulator();
    }
}
