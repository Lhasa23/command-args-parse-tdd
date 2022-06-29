package tdd.args;

public class TooManyArgumentsException extends RuntimeException {
    private final Option option;
    public TooManyArgumentsException(Option option) {
        this.option = option;
    }

    public String getOption() {
        return this.option.value();
    }
}
