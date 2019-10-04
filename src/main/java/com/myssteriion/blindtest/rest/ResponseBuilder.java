package com.myssteriion.blindtest.rest;

import java.util.List;

import com.myssteriion.blindtest.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.base.ErrorMessage;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;

@ControllerAdvice
public class ResponseBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBuilder.class);
	
	
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> create404(Exception e) {
	    
		LOGGER.error("not found", e);
		
		ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorMessage> create409(Exception e) {
	    
		LOGGER.error("already exists", e);
		
		ErrorMessage error = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage(), e.getCause());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> create500(Exception e) {
	    
		LOGGER.error("technical error", e);
		
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	public static <T extends IModel> ResponseEntity<T> create200(T iModel) {
		
		Tool.verifyValue("iModel", iModel);
		return new ResponseEntity<>(iModel, HttpStatus.OK);
	}
	
	public static <T extends IModel> ResponseEntity< ListDTO<T> > create200(List<T> iModels) {
		
		ListDTO<T> list = new ListDTO<>(iModels);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	public static <T extends IModel> ResponseEntity<T> create201(T iModel) {
		
		Tool.verifyValue("iModel", iModel);
		return new ResponseEntity<>(iModel, HttpStatus.CREATED);
	}
	
	public static ResponseEntity<Empty> create204() {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
