package tdd.args.exceptions;

import tdd.args.Option;

public class IllegalValueException extends RuntimeException {
	private final Option option;
	private final String value;

	public IllegalValueException(Option option, String value) {
		this.option = option;
		this.value = value;
	}

	public String getOption() {
		return this.option.value();
	}

	public String getValue() {
		return this.value;
	}
}
