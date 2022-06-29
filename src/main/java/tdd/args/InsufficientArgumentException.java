package tdd.args;

public class InsufficientArgumentException extends RuntimeException {
    private final Option option;
    public InsufficientArgumentException(Option option) {
        this.option = option;
    }

    public String getOption() {
        return this.option.value();
    }
}
