package tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
    // -l -p 8080 -d /usr/logs
    // index: [-l], [-p, 8080], [-d, /usr/logs]
    // Map: {-l:[], -p:[8080], -d:[.usr/logs]}
    // happy path:
    // single test:
    // TODO:  -l => true | null => false
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");

        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_true_if_flag_empty() {
        BooleanOption option = Args.parse(BooleanOption.class);

        assertFalse(option.logging());
    }

    static record BooleanOption(@Option("l") boolean logging) {
    }

    // TODO: -p 8080 => 8080
    // TODO: -d /usr/logs => /usr/logs
    // multi test:
    // TODO: -l -p 8080 -d /usr/logs => logging: true, port: 8080, directory: /usr/logs
    // sad path:
    // TODO: -l t / -l t f => error
    // TODO: -p / -p 8080 8081 => error
    // TODO: -d / -d /usr/logs /usr/ssh => error
    // default value:
    // TODO: -l => false
    // TODO: -p => 0
    // TODO: -d => ""

    @Test
    @Disabled
    public void should_example_1() {
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    // -g this is a list -d 1 2 -3 5
//    @Test
//    @Disabled
//    public void should_example_2() {
//        ListOptions options = Args.parse(Options.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
//
//        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
//        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
//    }
//
//    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
//    }
}
