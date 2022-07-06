package tdd.args;

import tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;

class BooleanOptionParser implements OptionsParser<Boolean> {

	@Override
	public Boolean parse(List<String> arguments, Option option) {
		return SingleValuedOptionParser.values(arguments, option, 0).isPresent();
	}
}
