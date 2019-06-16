package com.myssteriion.blindtest.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.tools.Tool;


public class RestBuilder {

	public static ResponseEntity createEmpty200() {
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public static ResponseEntity<RestError> create500(String message, Throwable cause) {

		RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
		return new ResponseEntity<RestError>(restError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
