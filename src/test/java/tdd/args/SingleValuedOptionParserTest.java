package tdd.args;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static tdd.args.BooleanOptionParserTest.option;

public class SingleValuedOptionParserTest {
    @Test
    public void should_not_accept_extra_argument_for_port_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValuedOptionParser<>(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_not_accept_extra_argument_for_directory_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValuedOptionParser<>("", Integer::parseInt).parse(asList("-d", "/usr/ssh", "/usr/logs"), option("d"));
        });

        assertEquals("d", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -d", "-p"})
    public void should_not_accept_insufficient_argument_for_port_option(String arguments) {
        InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
            new SingleValuedOptionParser<>(0, Integer::parseInt).parse(asList(arguments.split(" ")), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-d", "-d -p"})
    public void should_not_accept_insufficient_argument_for_directory_option(String arguments) {
        InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
            new SingleValuedOptionParser<>("", String::valueOf).parse(asList(arguments.split(" ")), option("d"));
        });

        assertEquals("d", e.getOption());
    }

    @Test
    public void should_set_default_value_0_for_port_option() {
        assertEquals(0,
                new SingleValuedOptionParser<>(0, Integer::parseInt).parse(asList(), option("p")));
    }

    @Test
    public void should_set_default_value_empty_for_directory_option() {
        assertEquals("",
                new SingleValuedOptionParser<>("", String::valueOf).parse(asList(), option("d")));
    }
}
