package com.myssteriion.blindtest.db.common;

import com.myssteriion.blindtest.tools.Tool;

public class NotFoundException extends Exception {

	public NotFoundException(String message) {
		
		super(message);
		Tool.verifyValue("message", message);
	}
	
}
