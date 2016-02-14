package com.andreev.rental.logic.util;

public interface IValidatorFactory {

	public RegexValidator getValidator(ERegexValidator type);

}
