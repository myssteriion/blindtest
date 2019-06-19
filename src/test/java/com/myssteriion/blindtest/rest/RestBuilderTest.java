package com.myssteriion.blindtest.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ErrorModel;
import com.myssteriion.blindtest.model.base.ListModel;
import com.myssteriion.blindtest.model.dto.ProfilDTO;

public class RestBuilderTest extends AbstractTest {

	@SuppressWarnings("unchecked")
	@Test
	public void create200() {
		
		ResponseEntity<?> re = RestBuilder.create200(null);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListModel<ProfilDTO> body = (ListModel<ProfilDTO>) re.getBody();
		Assert.assertTrue( body.getItems().isEmpty() );
	}
	
	@Test
	public void create204() {
		
		ResponseEntity<?> re = RestBuilder.create204();
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}

	@Test
	public void create500() {

		String message = "message";
		NullPointerException npe = new NullPointerException("npe");
		
		
		ResponseEntity<?> re = RestBuilder.create500(message, npe);
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode() );
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, ((ErrorModel) re.getBody()).getStatus() );
		Assert.assertEquals( message, ((ErrorModel) re.getBody()).getMessage() );
		Assert.assertEquals( "npe", ((ErrorModel) re.getBody()).getCauses().get(0) );
	}

}
