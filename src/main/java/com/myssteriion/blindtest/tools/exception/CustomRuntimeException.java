package com.myssteriion.blindtest.tools.exception;

import com.myssteriion.blindtest.tools.Tool;

public class CustomRuntimeException extends RuntimeException {

	public CustomRuntimeException(String message, Throwable throwable) {
		
		super(message, throwable);
		Tool.verifyValue("message", message);
		Tool.verifyValue("throwable", throwable);
	}
	
}
