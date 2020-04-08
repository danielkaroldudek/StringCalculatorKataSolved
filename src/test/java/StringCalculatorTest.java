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
}
