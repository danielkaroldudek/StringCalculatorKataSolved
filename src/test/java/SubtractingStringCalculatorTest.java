import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tdd.calc.StringCalculator;

import static org.assertj.core.api.Assertions.assertThat;

public class SubtractingStringCalculatorTest {
    private StringCalculator sut;

    @BeforeEach
    public void init() {
        sut = new StringCalculator();
    }

    @Test
    public void shouldReturnZeroWhenGotEmptyString() {
        assertThat(sut.sub("")).isEqualTo("0");
    }
}
