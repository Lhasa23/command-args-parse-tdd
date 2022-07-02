package tdd.args;

import java.lang.reflect.Parameter;

public class IllegalOptionException extends RuntimeException {
    private final Parameter parameter;
    public IllegalOptionException(Parameter parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return this.parameter.getName();
    }
}
