package com.myssteriion.blindtest.rest;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.myssteriion.blindtest.model.AbstractDTO;
import com.myssteriion.blindtest.model.base.EmptyModel;
import com.myssteriion.blindtest.model.base.ErrorModel;
import com.myssteriion.blindtest.model.base.ListModel;


public class RestBuilder {

	public static <T extends AbstractDTO> ResponseIModel< ListModel<T> > create200(List<T> dto) {
		
		ListModel<T> list = new ListModel<>(dto);
		return new ResponseIModel< ListModel<T> >(list, HttpStatus.OK);
	}
	
	public static ResponseIModel<EmptyModel> create204() {
		return new ResponseIModel<EmptyModel>(HttpStatus.NO_CONTENT);
	}
	
	public static ResponseIModel<ErrorModel> create500(String message, Throwable cause) {

		ErrorModel restError = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
		return new ResponseIModel<ErrorModel>(restError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
