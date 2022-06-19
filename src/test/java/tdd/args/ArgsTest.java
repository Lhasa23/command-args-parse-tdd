package tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
    // -l -p 8080 -d /usr/logs
    // index: [-l], [-p, 8080], [-d, /usr/logs]
    // Map: {-l:[], -p:[8080], -d:[.usr/logs]}
    @Test
    public void should_set_boolean_option_to_true_if_l_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_true_if_l_flag_empty() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    static record BooleanOption(@Option("l") boolean logging) {
    }

    @Test
    public void should_set_int_to_port_if_p_flag_present() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    static record IntOption(@Option("p") int port) {
    }

    @Test
    public void should_set_string_to_dir_if_d_flag_present() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    static record StringOption(@Option("d") String directory) {
    }

    @Test
    public void should_parse_multi_args() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }


    // sad path:
    // TODO: -l t / -l t f => error
    // TODO: -p / -p 8080 8081 => error
    // TODO: -d / -d /usr/logs /usr/ssh => error
    // default value:
    // TODO: -l => false
    // TODO: -p => 0
    // TODO: -d => ""



    // -g this is a list -d 1 2 -3 5
    @Test
    @Disabled
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");

        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }
}
