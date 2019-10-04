package com.myssteriion.blindtest.rest.exception;

import com.myssteriion.blindtest.tools.Tool;

/**
 * The type Not found exception.
 */
public class NotFoundException extends Exception {

	/**
	 * Instantiates a new Not found exception.
	 *
	 * @param message the message
	 */
	public NotFoundException(String message) {
		
		super(message);
		Tool.verifyValue("message", message);
	}
	
}
