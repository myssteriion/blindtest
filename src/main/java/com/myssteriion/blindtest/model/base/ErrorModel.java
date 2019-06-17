package com.myssteriion.blindtest.model.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.model.IModel;
import com.myssteriion.blindtest.tools.Tool;

public class ErrorModel implements IModel {
	
	private HttpStatus status;
	
	private String message;

	private List<String> causes;
	
	
	
	public ErrorModel(HttpStatus status, String message) {
		this(status, message, null);
	}

	public ErrorModel(HttpStatus status, String message, Throwable e) {
		
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
