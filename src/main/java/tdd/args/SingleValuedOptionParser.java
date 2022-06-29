package tdd.args;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionsParser<T> {
    Function<String, T> valueParser;

    public SingleValuedOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int valueIndex = arguments.indexOf("-" + option.value()) + 1;
        int argIndex = valueIndex + 1;
        if (argIndex < arguments.size() && !arguments.get(argIndex).startsWith("-"))
            throw new TooManyArgumentsException(option);
        if (valueIndex == arguments.size() || arguments.get(valueIndex).startsWith("-"))
            throw new InsufficientArgumentException(option);
        String value = arguments.get(valueIndex);
        return valueParser.apply(value);
    }
}
