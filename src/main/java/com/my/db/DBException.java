package com.my.db;

public class DBException extends Exception {

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(Throwable cause) {
		super(cause);
	}
}
