package tdd.args;

import java.util.List;

class IntParser implements OptionsParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        return Integer.parseInt(arguments.get(arguments.indexOf("-" + option.value()) + 1));
    }
}
