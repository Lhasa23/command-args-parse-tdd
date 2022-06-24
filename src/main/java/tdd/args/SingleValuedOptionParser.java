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
        int index = arguments.indexOf("-" + option.value()) + 1;
        String value = arguments.get(index);
        return valueParser.apply(value);
    }
}