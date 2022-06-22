package tdd.args;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanOptionParserTest {
    // BooleanOptionParserTest
    // sad path:
    // TODO: -l t / -l t f => error
    // default value:
    // TODO: -l => false
    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> new BooleanOptionParser().parse(asList("-l", "t"), option("l")));
        assertEquals("l", e.getOption());
    }

    static Option option(String value) {
        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
