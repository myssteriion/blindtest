package com.myssteriion.blindtest.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ErrorMessage;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;

public class ResponseBuilderTest extends AbstractTest {

	@Test
	public void catchException() {
		
		ResponseBuilder rb = new ResponseBuilder();
		ResponseEntity<ErrorMessage> re = rb.catchException( new IllegalArgumentException("iae") );
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode() );
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getBody().getStatus() );
		Assert.assertEquals( "iae", re.getBody().getMessage() );
	}
	
	@Test
	public void create200() {
		
		ResponseEntity< ListDTO<ProfilDTO> > re = ResponseBuilder.create200(null);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListDTO<ProfilDTO> body = re.getBody();
		Assert.assertTrue( body.getItems().isEmpty() );
	}
	
	@Test
	public void create204() {
		
		ResponseEntity<?> re = ResponseBuilder.create204();
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}

}
