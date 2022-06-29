package tdd.args;

import java.util.List;

class BooleanOptionParser implements OptionsParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        int argIndex = index + 1;
        if (argIndex < arguments.size() && !arguments.get(argIndex).startsWith("-"))
            throw new TooManyArgumentsException(option);
        return index != -1;
    }
}
