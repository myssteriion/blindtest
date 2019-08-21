package com.myssteriion.blindtest.db.exception;

import com.myssteriion.blindtest.tools.Tool;

public class DaoException extends Exception {

	public DaoException(String message, Throwable cause) {
		
		super(message, cause);
		
		Tool.verifyValue("message", message);
		Tool.verifyValue("cause", cause);
	}

	public DaoException(String message) {
		
		super(message);
		
		Tool.verifyValue("message", message);
	}
	
}
