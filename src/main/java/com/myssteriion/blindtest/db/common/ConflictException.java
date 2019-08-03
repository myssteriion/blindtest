package com.myssteriion.blindtest.db.common;

import com.myssteriion.blindtest.tools.Tool;

public class ConflictException extends Exception {

	public ConflictException(String message) {
		
		super(message);
		Tool.verifyValue("message", message);
	}
	
}
