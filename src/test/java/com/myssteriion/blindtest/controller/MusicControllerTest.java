package com.myssteriion.blindtest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.rest.RestError;
import com.myssteriion.blindtest.service.MusicService;

public class MusicControllerTest extends AbstractTest {

	@Mock
	private MusicService service;
	
	@InjectMocks
	private MusicController controller;
	
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void refresh() throws EntityManagerException {

//		NullPointerException npe = new NullPointerException("npe");
//		Mockito.doThrow(npe).doNothing().when(service).refresh();
		Mockito.doNothing().when(service).refresh();

		ResponseEntity<RestError> re = controller.refresh();
//		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode() );
//		Assert.assertEquals( "Can't refresh musics.", ((RestError) re.getBody()).getMessage() );
//		Assert.assertEquals( "npe", ((RestError) re.getBody()).getCauses().get(0) );
		
//		re = controller.refresh();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
	}

}
