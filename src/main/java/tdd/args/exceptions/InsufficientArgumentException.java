package tdd.args.exceptions;

import tdd.args.Option;

public class InsufficientArgumentException extends RuntimeException {
	private final Option option;

	public InsufficientArgumentException(Option option) {
		this.option = option;
	}

	public String getOption() {
		return this.option.value();
	}
}
