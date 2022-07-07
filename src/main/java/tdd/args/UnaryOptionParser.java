package tdd.args;

import tdd.args.exceptions.IllegalValueException;
import tdd.args.exceptions.InsufficientArgumentException;
import tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class UnaryOptionParser {

	public static OptionsParser<Boolean> booleanOptionParser() {
		return ((arguments, option) -> values(arguments, option, 0).map(it -> true)
																   .orElse(false));
	}

	public static <T> OptionsParser<T> unaryOptionParser(T defaultValue, Function<String, T> valueParser) {
		return ((arguments, option) -> values(arguments, option, 1).map(it -> parserValue(option, it.get(0), valueParser))
																   .orElse(defaultValue));
	}

	private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
		int index = arguments.indexOf("-" + option.value());

		if (index == -1)
			return Optional.empty();

		List<String> values = getOptionsValue(arguments, index + 1);
		if (values.size() > expectedSize)
			throw new TooManyArgumentsException(option);
		if (values.size() < expectedSize)
			throw new InsufficientArgumentException(option);
		return Optional.of(values);
	}

	private static List<String> getOptionsValue(List<String> arguments, int valueIndex) {
		return arguments.subList(valueIndex,
								 IntStream.range(valueIndex, arguments.size())
										  .filter(it -> arguments.get(it)
																 .startsWith("-"))
										  .findFirst()
										  .orElse(arguments.size()));
	}

	private static <T> T parserValue(Option option, String value, Function<String, T> valueParser) {
		try {
			return valueParser.apply(value);
		} catch (Exception e) {
			throw new IllegalValueException(option, value);
		}
	}
}
