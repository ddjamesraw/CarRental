package com.andreev.rental.logic.util;

public class PasswordEncoder {

	public PasswordEncoder() {
	}

	public int encode(String password) {
		int i = password.hashCode();
		return i;
	}

}
