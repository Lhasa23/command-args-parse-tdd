package tdd.args;

import java.util.List;
import java.util.function.Function;

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
        int valueIndex = index + 1;
        int argIndex = valueIndex + 1;
        if (index == -1) return defaultValue;
        if (argIndex < arguments.size() && !arguments.get(argIndex).startsWith("-"))
            throw new TooManyArgumentsException(option);
        if (valueIndex == arguments.size() || arguments.get(valueIndex).startsWith("-"))
            throw new InsufficientArgumentException(option);
        String value = arguments.get(valueIndex);
        return valueParser.apply(value);
    }
}
