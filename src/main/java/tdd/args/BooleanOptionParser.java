package tdd.args;

import java.util.List;

class BooleanOptionParser implements OptionsParser {

    @Override
    public Object parse(List<String> arguments, Option option) {
        return arguments.contains("-" + option.value());
    }
}
