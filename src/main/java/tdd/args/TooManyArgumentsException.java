package tdd.args;

public class TooManyArgumentsException extends Exception {
    public TooManyArgumentsException() {
    }

    public String getOption() {
        return "l";
    }
}
