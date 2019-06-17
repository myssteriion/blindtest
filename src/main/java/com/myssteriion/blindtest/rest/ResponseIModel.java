package com.myssteriion.blindtest.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.model.IModel;
import com.myssteriion.blindtest.tools.Tool;

public class ResponseIModel<T extends IModel> extends ResponseEntity<T> {

	public ResponseIModel(HttpStatus status) {
		
		super(status);
		Tool.verifyValue("status", status);
	}

	public ResponseIModel(T body, HttpStatus status) {
		
		super(body, status);
		Tool.verifyValue("body", body);
		Tool.verifyValue("status", status);
	}

}
