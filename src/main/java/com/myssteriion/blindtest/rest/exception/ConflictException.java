package com.myssteriion.blindtest.rest.exception;

import com.myssteriion.blindtest.tools.Tool;

/**
 * The Conflict exception.
 */
public class ConflictException extends Exception {

	/**
	 * Instantiates a new Conflict exception.
	 *
	 * @param message the message
	 */
	public ConflictException(String message) {
		
		super(message);
		Tool.verifyValue("message", message);
	}
	
}
