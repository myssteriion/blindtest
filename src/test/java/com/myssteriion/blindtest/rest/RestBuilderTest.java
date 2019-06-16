package com.myssteriion.blindtest.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;

public class RestBuilderTest extends AbstractTest {

	@Test
	public void createEmpty200() {
		
		ResponseEntity<?> re = RestBuilder.createEmpty200();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
	}

	@Test
	public void create500() {

		String message = "message";
		NullPointerException npe = new NullPointerException("npe");
		
		
		ResponseEntity<?> re = RestBuilder.create500(message, npe);
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode() );
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, ((RestError) re.getBody()).getStatus() );
		Assert.assertEquals( message, ((RestError) re.getBody()).getMessage() );
		Assert.assertEquals( "npe", ((RestError) re.getBody()).getCauses().get(0) );
	}

}
