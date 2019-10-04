package com.myssteriion.blindtest.db.exception;

import com.myssteriion.blindtest.tools.Tool;

/**
 * DB exception.
 */
public class DaoException extends Exception {

	/**
	 * Instantiates a new Dao exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public DaoException(String message, Throwable cause) {
		
		super(message, cause);
		
		Tool.verifyValue("message", message);
		Tool.verifyValue("cause", cause);
	}

	/**
	 * Instantiates a new Dao exception.
	 *
	 * @param message the message
	 */
	public DaoException(String message) {
		
		super(message);
		
		Tool.verifyValue("message", message);
	}
	
}
