package tdd.args;

import java.util.List;

interface OptionsParser<T> {
    T parse(List<String> arguments, Option option);
}
