package com.myssteriion.blindtest.rest;

import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.model.base.EmptyModel;
import com.myssteriion.blindtest.model.base.ErrorModel;


public class RestBuilder {

	public static ResponseIModel<EmptyModel> create204() {
		return new ResponseIModel<EmptyModel>(HttpStatus.NO_CONTENT);
	}
	
	public static ResponseIModel<ErrorModel> create500(String message, Throwable cause) {

		ErrorModel restError = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
		return new ResponseIModel<ErrorModel>(restError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
