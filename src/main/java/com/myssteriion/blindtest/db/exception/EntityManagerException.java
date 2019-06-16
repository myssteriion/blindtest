package com.myssteriion.blindtest.db.exception;

import com.myssteriion.blindtest.tools.Tool;

public class EntityManagerException extends Exception {

	public EntityManagerException(String message, Throwable cause) {
		
		super(message, cause);
		
		Tool.verifyValue("message", message);
		Tool.verifyValue("cause", cause);
	}

	public EntityManagerException(String message) {
		
		super(message);
		
		Tool.verifyValue("message", message);
	}
	
}
