package tdd.args.exceptions;

import tdd.args.Option;

public class TooManyArgumentsException extends RuntimeException {
	private final Option option;

	public TooManyArgumentsException(Option option) {
		this.option = option;
	}

	public String getOption() {
		return this.option.value();
	}
}
