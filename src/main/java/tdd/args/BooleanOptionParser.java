package tdd.args;

import java.util.List;

class BooleanOptionParser implements OptionsParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        int nextIndex = index + 1;
        if (nextIndex < arguments.size() && !arguments.get(nextIndex).startsWith("-"))
            throw new TooManyArgumentsException(option);
        return index != -1;
    }
}
