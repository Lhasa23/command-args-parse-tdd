package tdd.args;

import tdd.args.exceptions.InsufficientArgumentException;
import tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingleValuedOptionParser<T> implements OptionsParser<T> {
	T defaultValue;
	Function<String, T> valueParser;

	public SingleValuedOptionParser(T defaultValue, Function<String, T> valueParser) {
		this.defaultValue = defaultValue;
		this.valueParser = valueParser;
	}

	@Override
	public T parse(List<String> arguments, Option option) {
		return values(arguments, option, 1).map(it -> valueParser.apply(it.get(0))).orElse(defaultValue);
	}

	static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
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

	static List<String> getOptionsValue(List<String> arguments, int valueIndex) {
		return arguments.subList(valueIndex,
								 IntStream.range(valueIndex, arguments.size())
										 .filter(it -> arguments.get(it).startsWith("-"))
										 .findFirst()
										 .orElse(arguments.size()));
	}
}
