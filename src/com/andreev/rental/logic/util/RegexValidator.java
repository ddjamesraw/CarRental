package com.andreev.rental.logic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator {
	
	private String message;	
	private Pattern pattern;
	
	public RegexValidator(String regex, String message) {
		this.pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
		this.message = message;
	}
	
	public boolean isValid(String string) {
		if (string == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(string);
		if(matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public String getMessage() {
		return message;
	}
}
