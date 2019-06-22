package com.myssteriion.blindtest.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.base.ErrorMessage;
import com.myssteriion.blindtest.model.base.ListDTO;

@ControllerAdvice
public class ResponseBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBuilder.class);
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> catchException(Exception e) {
	    
		LOGGER.error("Technical error", e);
		
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	public static <T extends AbstractDTO> ResponseEntity< ListDTO<T> > create200(List<T> dto) {
		
		ListDTO<T> list = new ListDTO<>(dto);
		return new ResponseEntity< ListDTO<T> >(list, HttpStatus.OK);
	}
	
	public static ResponseEntity<Empty> create204() {
		return new ResponseEntity<Empty>(HttpStatus.NO_CONTENT);
	}
	
}
