package tdd.args;

import org.junit.jupiter.api.Test;
import tdd.args.exceptions.TooManyArgumentsException;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanOptionParserTest {

	@Test // happy path
	public void should_true_if_boolean_option_present() {
		assertTrue(new BooleanOptionParser().parse(asList("-l"), option("l")));
	}

	@Test // sad path
	public void should_not_accept_extra_argument_for_boolean_option() {
		TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> new BooleanOptionParser().parse(asList("-l", "t"), option("l")));
		assertEquals("l", e.getOption());
	}

	@Test // default value
	public void should_false_if_boolean_option_is_empty() {
		assertFalse(new BooleanOptionParser().parse(asList(), option("l")));
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
