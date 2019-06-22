package com.myssteriion.blindtest.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.base.EmptyModel;
import com.myssteriion.blindtest.model.base.ErrorModel;
import com.myssteriion.blindtest.model.base.ListModel;

@ControllerAdvice
public class RestBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestBuilder.class);
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorModel> catchException(Exception e) {
	    
		LOGGER.error("Technical error", e);
		
		ErrorModel error = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
		return new ResponseEntity<ErrorModel>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	public static <T extends AbstractDTO> ResponseEntity< ListModel<T> > create200(List<T> dto) {
		
		ListModel<T> list = new ListModel<>(dto);
		return new ResponseEntity< ListModel<T> >(list, HttpStatus.OK);
	}
	
	public static ResponseEntity<EmptyModel> create204() {
		return new ResponseEntity<EmptyModel>(HttpStatus.NO_CONTENT);
	}
	
}
