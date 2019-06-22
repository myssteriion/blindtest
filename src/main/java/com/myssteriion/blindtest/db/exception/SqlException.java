package com.myssteriion.blindtest.db.exception;

import com.myssteriion.blindtest.tools.Tool;

public class SqlException extends Exception {

	public SqlException(String message, Throwable cause) {
		
		super(message, cause);
		
		Tool.verifyValue("message", message);
		Tool.verifyValue("cause", cause);
	}

	public SqlException(String message) {
		
		super(message);
		
		Tool.verifyValue("message", message);
	}
	
}
