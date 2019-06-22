package com.myssteriion.blindtest.db.common;

import com.myssteriion.blindtest.tools.Tool;

public class AlreadyExistsException extends Exception {

	public AlreadyExistsException(String message) {
		
		super(message);
		Tool.verifyValue("message", message);
	}
	
}
