package com.myssteriion.blindtest.db.exception;

public class DbException extends Exception {

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbException(String message) {
		super(message);
	}
	
}
