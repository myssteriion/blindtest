package com.myssteriion.blindtest.tools.exception;

import com.myssteriion.blindtest.tools.Tool;

/**
 * The type Custom runtime exception.
 */
public class CustomRuntimeException extends RuntimeException {

	/**
	 * Instantiates a new Custom runtime exception.
	 *
	 * @param message   the message
	 * @param throwable the throwable
	 */
	public CustomRuntimeException(String message, Throwable throwable) {
		
		super(message, throwable);
		Tool.verifyValue("message", message);
		Tool.verifyValue("throwable", throwable);
	}
	
}
