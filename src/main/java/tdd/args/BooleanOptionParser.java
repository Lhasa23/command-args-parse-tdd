package tdd.args;

import java.util.List;

class BooleanOptionParser implements OptionsParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (!arguments.get(index + 1).startsWith("-")) throw new TooManyArgumentsException(option);
        return index != -1;
    }
}
