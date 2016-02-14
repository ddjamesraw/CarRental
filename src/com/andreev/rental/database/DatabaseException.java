package com.andreev.rental.database;


public class DatabaseException extends Exception {

	private static final long serialVersionUID = -4292782163456074368L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}
	
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DatabaseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
