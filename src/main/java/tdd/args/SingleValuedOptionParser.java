package tdd.args;

import java.util.List;
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
		int index = arguments.indexOf("-" + option.value());
		if (index == -1)
			return defaultValue;

		List<String> values = getOptionsValue(arguments, index + 1);

		if (values.size() > 1)
			throw new TooManyArgumentsException(option);
		if (values.size() < 1)
			throw new InsufficientArgumentException(option);
		String value = values.get(0);
		return valueParser.apply(value);
	}

	static List<String> getOptionsValue(List<String> arguments, int valueIndex) {
		return arguments.subList(valueIndex,
								 IntStream.range(valueIndex, arguments.size())
										 .filter(it -> arguments.get(it).startsWith("-"))
										 .findFirst()
										 .orElse(arguments.size()));
	}
}
