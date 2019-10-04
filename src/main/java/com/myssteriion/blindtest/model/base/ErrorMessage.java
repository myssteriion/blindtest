package com.myssteriion.blindtest.model.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.model.IModel;
import com.myssteriion.blindtest.tools.Tool;

/**
 * Use when REST service return an error.
 */
public class ErrorMessage implements IModel {

	/**
	 * The HTTP status.
	 */
	private HttpStatus status;

	/**
	 * The message.
	 */
	private String message;

	/**
	 * The cause(s).
	 */
	private List<String> causes;


	/**
	 * Instantiates a new Error message.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public ErrorMessage(HttpStatus status, String message) {
		this(status, message, null);
	}

	/**
	 * Instantiates a new Error message.
	 *
	 * @param status  the status
	 * @param message the message
	 * @param cause   the cause
	 */
	public ErrorMessage(HttpStatus status, String message, Throwable cause) {
		
		Tool.verifyValue("status", status);
		Tool.verifyValue("message", message);
		
		this.status = status;
		this.message = message;
		this.causes = (cause == null) ? new ArrayList<>() : Tool.transformToList(cause);
	}


	/**
	 * Gets status.
	 *
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Gets message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets causes.
	 *
	 * @return the causes
	 */
	public List<String> getCauses() {
		return causes;
	}
	
}
