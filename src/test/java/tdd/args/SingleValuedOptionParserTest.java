package tdd.args;


import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static tdd.args.BooleanOptionParserTest.option;

public class SingleValuedOptionParserTest {
    @Test
    public void should_not_accept_extra_argument_for_port_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValuedOptionParser<>(Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_not_accept_extra_argument_for_directory_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValuedOptionParser<>(Integer::parseInt).parse(asList("-d", "/usr/ssh", "/usr/logs"), option("d"));
        });

        assertEquals("d", e.getOption());
    }
    // default value:
    // TODO: -p => 0
    @Test
    public void should_not_accept_insufficient_argument_for_port_option() {
        InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
            new SingleValuedOptionParser<>(Integer::parseInt).parse(asList("-p"), option("p"));
        });

        assertEquals("p", e.getOption());
    }
    // TODO: -d => ""
}
