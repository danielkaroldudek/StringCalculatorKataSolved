import ParameterResolvers.ConverterParameterResolver;
import ParameterResolvers.ErrorMessagesParameterResolver;
import ParameterResolvers.InputValidatorFactoryParameterResolver;
import ParameterResolvers.StringManipulatorParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tdd.calc.IStringCalculator;
import org.tdd.calc.StringCalculator;
import org.tdd.calc.conversion.IConverter;
import org.tdd.calc.manipulation.IStringManipulator;
import org.tdd.calc.messaging.IErrorMessages;
import org.tdd.calc.validation.IInputValidatorFactory;
import org.tdd.calc.validation.StringCalculatorValidation;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ConverterParameterResolver.class)
@ExtendWith(ErrorMessagesParameterResolver.class)
@ExtendWith(StringManipulatorParameterResolver.class)
@ExtendWith({InputValidatorFactoryParameterResolver.class})
class SubtractingStringCalculatorTest {
    private IStringCalculator sut;

    @BeforeEach
    void init(IConverter converter, IErrorMessages errorMessages, IStringManipulator stringManipulator,
                     IInputValidatorFactory inputValidatorFactory) {
        sut = new StringCalculatorValidation(new StringCalculator(converter, stringManipulator),
                stringManipulator, errorMessages, inputValidatorFactory);
    }

    @Test
    void shouldReturnZeroWhenGotEmptyString() {
        assertThat(sut.sub("")).isEqualTo("0");
    }

    @Test
    void shouldReturnNumberWhenGivenNumber() {
        assertThat(sut.sub("1")).isEqualTo("1");
    }

    @Test
    void shouldReturnSubOfTwoNumbersWhenSeparatedByComma() {
        assertThat(sut.sub("1,2")).isEqualTo("-1");
    }

    @Test
    void shouldReturnSubOfTwoDoubleNumbersWhenSeparatedByComma() {
        assertThat(sut.sub("1.1,1.2")).isEqualTo("-0.1");
    }

    @Test
    void shouldReturnSubOfMultipleNumbersWhenSeparatedByComma() {
        assertThat(sut.sub("1,2.2,3,4.4,5")).isEqualTo("-13.6");
    }

    @Test
    void shouldReturnSubOfNumbersWhenSeparatedByCommaAndNewLine() {
        assertThat(sut.sub("1\n2,3")).isEqualTo("-4");
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnErrorMessageWithIndexWhenSeparatorsNextToEachOther(String[] input) {
        assertThat(sut.sub(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnErrorMessageWithIndexWhenSeparatorsNextToEachOther() {
        return Stream.of(
                Arguments.of((Object) new String[] {
                        "175.2,\n35", "Number expected but '\\n' found at position 6"
                }),
                Arguments.of((Object) new String[] {
                        "175.2\n,35", "Number expected but ',' found at position 6"
                })
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnEOFExceptionWhenSeparatorInLastPosition(String[] input) {
        assertThat(sut.sub(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnEOFExceptionWhenSeparatorInLastPosition() {
        return Stream.of(
                Arguments.of((Object) new String[] {
                        "1,3,", "Number expected but EOF found"
                }),
                Arguments.of((Object) new String[] {
                        "1\n3\n", "Number expected but EOF found"
                })
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnSubWhenSeparatedByCustomSeparator(String[] input) {
        assertThat(sut.sub(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnSubWhenSeparatedByCustomSeparator() {
        return Stream.of(
                Arguments.of((Object) new String[]{"//;\n1;2", "-1"}),
                Arguments.of((Object) new String[]{"//|\n1|2|3", "-4"}),
                Arguments.of((Object) new String[]{"//sep\n2sep3", "-1"})
        );
    }

    @Test
    void shouldReturnExceptionWhenTwoSeparationsInCustomSeparator() {
        assertThat(sut.sub("//|\n1|2,3"))
                .isEqualTo("'|' expected but ',' found at position 3");
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnExceptionWhenAtLeastOneNegativeNumberProvided(String[] input) {
        assertThat(sut.sub(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnExceptionWhenAtLeastOneNegativeNumberProvided() {
        return Stream.of(
                Arguments.of((Object) new String[]{"-1,2", "Negative not allowed : -1"}),
                Arguments.of((Object) new String[]{"2,-4,-5", "Negative not allowed : -4, -5"}),
                Arguments.of((Object) new String[]{"2,-4.1,-5", "Negative not allowed : -4.1, -5"})
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnMultipleExceptionsWhenMultipleErrorsFound(String[] input) {
        assertThat(sut.sub(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnMultipleExceptionsWhenMultipleErrorsFound() {
        return Stream.of(
                Arguments.of((Object) new String[]{"-1,,2", "Number expected but ',' found at position 2\\nNegative not allowed : -1"}),
                Arguments.of((Object) new String[]{"-1\n\n2", "Number expected but '\\n' found at position 2\\nNegative not allowed : -1"})
        );
    }
}
