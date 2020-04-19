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
class AddingStringCalculatorTest {
    private IStringCalculator sut;

    @BeforeEach
    void init(IConverter converter, IErrorMessages errorMessages, IStringManipulator stringManipulator,
                     IInputValidatorFactory inputValidatorFactory) {
        sut = new StringCalculatorValidation(new StringCalculator(converter, stringManipulator),
                stringManipulator, errorMessages, inputValidatorFactory);
    }

    @Test
    void shouldReturnZeroWhenGotEmptyString() {
        assertThat(sut.add("")).isEqualTo("0");
    }

    @Test
    void shouldReturnNumberWhenGivenNumber() {
        assertThat(sut.add("1")).isEqualTo("1");
    }

    @Test
    void shouldReturnSumOfTwoNumbersWhenSeparatedByComma() {
        assertThat(sut.add("1,2")).isEqualTo("3");
    }

    @Test
    void shouldReturnSumOfTwoDoubleNumbersWhenSeparatedByComma() {
        assertThat(sut.add("1.1,1.2")).isEqualTo("2.3");
    }

    @Test
    void shouldReturnSumOfMultipleNumbersWhenSeparatedByComma() {
        assertThat(sut.add("1,2.2,3,4.4,5")).isEqualTo("15.6");
    }

    @Test
    void shouldReturnSumOfNumbersWhenSeparatedByCommaAndNewLine() {
        assertThat(sut.add("1\n2,3")).isEqualTo("6");
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnErrorMessageWithIndexWhenSeparatorsNextToEachOther(String[] input) {
        assertThat(sut.add(input[0])).isEqualTo(input[1]);
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
        assertThat(sut.add(input[0])).isEqualTo(input[1]);
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
    void shouldReturnSumWhenSeparatedByCustomSeparator(String[] input) {
        assertThat(sut.add(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnSumWhenSeparatedByCustomSeparator() {
        return Stream.of(
                Arguments.of((Object) new String[]{"//;\n1;2", "3"}),
                Arguments.of((Object) new String[]{"//|\n1|2|3", "6"}),
                Arguments.of((Object) new String[]{"//sep\n2sep3", "5"})
        );
    }

    @Test
    void shouldReturnExceptionWhenTwoSeparationsInCustomSeparator() {
        assertThat(sut.add("//|\n1|2,3"))
                .isEqualTo("'|' expected but ',' found at position 3");
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnExceptionWhenAtLeastOneNegativeNumberProvided(String[] input) {
        assertThat(sut.add(input[0])).isEqualTo(input[1]);
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
        assertThat(sut.add(input[0])).isEqualTo(input[1]);
    }

    static Stream<Arguments> shouldReturnMultipleExceptionsWhenMultipleErrorsFound() {
        return Stream.of(
                Arguments.of((Object) new String[]{"-1,,2", "Number expected but ',' found at position 2\\nNegative not allowed : -1"}),
                Arguments.of((Object) new String[]{"-1\n\n2", "Number expected but '\\n' found at position 2\\nNegative not allowed : -1"})
        );
    }
}
