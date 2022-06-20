package tdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(arguments, it)).toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Option option = parameter.getAnnotation(Option.class);

        Object value = null;

        if (parameter.getType() == boolean.class) {
            value = BooleanParse(arguments, option);
        }

        if (parameter.getType() == int.class) {
            value = IntParse(arguments, option);
        }

        if (parameter.getType() == String.class) {
            value = StringParse(arguments, option);
        }
        return value;
    }

    interface OptionsParser {
        Object parse(List<String> arguments, Option option);
    }

    private static Object StringParse(List<String> arguments, Option option) {
        return new StringParser().parse(arguments, option);
    }

    static class StringParser implements OptionsParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.get(arguments.indexOf("-" + option.value()) + 1);
        }
    }

    private static Object IntParse(List<String> arguments, Option option) {
        return new IntParser().parse(arguments, option);
    }

    static class IntParser implements OptionsParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return Integer.parseInt(arguments.get(arguments.indexOf("-" + option.value()) + 1));
        }
    }

    private static Object BooleanParse(List<String> arguments, Option option) {
        return new BooleanParser().parse(arguments, option);
    }

    static class BooleanParser implements OptionsParser {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains("-" + option.value());
        }
    }
}
