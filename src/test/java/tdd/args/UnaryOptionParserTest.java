package tdd.args;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tdd.args.exceptions.IllegalValueException;
import tdd.args.exceptions.InsufficientArgumentException;
import tdd.args.exceptions.TooManyArgumentsException;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class UnaryOptionParserTest {
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

	@Nested
	class SingleValuedOptionParserTest {
		@Test // p happy path
		public void should_set_8080_for_port_option_argument() {
			Object parsed = new Object();
			Function<String, Object> parse = (it) -> parsed;
			Object whatever = new Object();
			assertSame(parsed,
					   UnaryOptionParser.unaryOptionParser(whatever, parse)
										.parse(asList("-p", "8080"), option("p")));
		}

		@Test // d happy path
		public void should_set_dir_for_directory_option_argument() {
			Object parsed = new Object();
			Function<String, Object> parse = (it) -> parsed;
			Object whatever = new Object();
			assertSame(parsed,
					   UnaryOptionParser.unaryOptionParser(whatever, parse)
										.parse(asList("-d", "/usr/logs"), option("d")));
		}

		@Test // p sad path
		public void should_not_accept_extra_argument_for_port_option() {
			TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
				UnaryOptionParser.unaryOptionParser(0, Integer::parseInt)
								 .parse(asList("-p", "8080", "8081"), option("p"));
			});

			assertEquals("p", e.getOption());
		}

		@Test
		public void should_not_accept_illegal_value_for_port_option() {
			IllegalValueException e = assertThrows(IllegalValueException.class, () -> {
				UnaryOptionParser.unaryOptionParser(0, Integer::parseInt)
								 .parse(asList("-p", "/usr/logs"), option("p"));
			});

			assertEquals("p", e.getOption());
			assertEquals("/usr/logs", e.getValue());
		}

		@Test // d sad path
		public void should_not_accept_extra_argument_for_directory_option() {
			TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
				UnaryOptionParser.unaryOptionParser("", Integer::parseInt)
								 .parse(asList("-d", "/usr/ssh", "/usr/logs"), option("d"));
			});

			assertEquals("d", e.getOption());
		}

		@ParameterizedTest // p sad path
		@ValueSource(strings = {"-p -d", "-p"})
		public void should_not_accept_insufficient_argument_for_port_option(String arguments) {
			InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
				UnaryOptionParser.unaryOptionParser(0, Integer::parseInt)
								 .parse(asList(arguments.split(" ")), option("p"));
			});

			assertEquals("p", e.getOption());
		}

		@ParameterizedTest // d sad path
		@ValueSource(strings = {"-d", "-d -p"})
		public void should_not_accept_insufficient_argument_for_directory_option(String arguments) {
			InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
				UnaryOptionParser.unaryOptionParser("", String::valueOf)
								 .parse(asList(arguments.split(" ")), option("d"));
			});

			assertEquals("d", e.getOption());
		}

		@Test // p default value
		public void should_set_default_value_0_for_port_option() {
			Function<String, Object> whatever = (it) -> null;
			Object defaultValue = new Object();
			assertSame(defaultValue,
					   UnaryOptionParser.unaryOptionParser(defaultValue, whatever)
										.parse(asList(), option("p")));
		}
	}

	@Nested
	class BooleanOptionParserTest {
		@Test // happy path
		public void should_true_if_boolean_option_present() {
			assertTrue(UnaryOptionParser.booleanOptionParser()
										.parse(asList("-l"), option("l")));
		}

		@Test // sad path
		public void should_not_accept_extra_argument_for_boolean_option() {
			TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> UnaryOptionParser.booleanOptionParser()
																											   .parse(asList("-l", "t"), option("l")));
			assertEquals("l", e.getOption());
		}

		@Test // default value
		public void should_false_if_boolean_option_is_empty() {
			assertFalse(UnaryOptionParser.booleanOptionParser()
										 .parse(asList(), option("l")));
		}
	}
}
