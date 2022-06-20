package tdd.args;

import java.util.List;

interface OptionsParser {
    Object parse(List<String> arguments, Option option);
}
