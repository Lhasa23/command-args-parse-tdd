package tdd.args;

import java.util.List;

class StringParser implements OptionsParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        return arguments.get(arguments.indexOf("-" + option.value()) + 1);
    }
}
