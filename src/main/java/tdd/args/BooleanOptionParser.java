package tdd.args;

import java.util.List;

class BooleanOptionParser implements OptionsParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1)
            return false;

        List<String> values = SingleValuedOptionParser.getOptionsValue(arguments, index + 1);
        if (values.size() > 0)
            throw new TooManyArgumentsException(option);

        return true;
    }
}
