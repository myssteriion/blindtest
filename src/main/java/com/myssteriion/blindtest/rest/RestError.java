package com.myssteriion.blindtest.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.tools.Tool;

public class RestError {
	
	private HttpStatus status;
	
	private String message;

	private List<String> causes;
	
	
	
	public RestError(HttpStatus status, String message) {
		this(status, message, null);
	}

	public RestError(HttpStatus status, String message, Throwable e) {
		
		Tool.verifyValue("status", status);
		Tool.verifyValue("message", message);
		
		this.status = status;
		this.message = message;
		this.causes = (e == null) ? new ArrayList<>() : Tool.transformToList(e);
	}
	
	
	
	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getCauses() {
		return causes;
	}
	
}
