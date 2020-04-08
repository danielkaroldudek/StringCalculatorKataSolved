import org.junit.Before;
import org.junit.Test;
import org.tdd.calc.StringCalculator;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCalculatorTest {
    private StringCalculator sut;

    @Before
    public void setUp() {
        sut = new StringCalculator();
    }

    @Test
    public void shouldReturnZeroWhenGotEmptyString() {
        assertThat(sut.add("")).isEqualTo("0");
    }

    @Test
    public void shouldReturnNumberWhenGivenNumber() {
        assertThat(sut.add("1")).isEqualTo("1");
    }

    @Test
    public void shouldReturnSumOfTwoNumbersWhenSeparatedByComma() {
        assertThat(sut.add("1,2")).isEqualTo("3");
    }

    @Test
    public void shouldReturnSumOfTwoDoubleNumbersWhenSeparatedByComma() {
        assertThat(sut.add("1.1,1.2")).isEqualTo("2.3");
    }

    @Test
    public void shouldReturnSumOfMultipleNumbersWhenSeparatedByComma() {
        assertThat(sut.add("1,2.2,3,4.4,5")).isEqualTo("15.6");
    }

    @Test
    public void shouldReturnSumOfNumbersWhenSeparatedByCommaAndNewLine() {
        assertThat(sut.add("1\n2,3")).isEqualTo("6");
    }
}
