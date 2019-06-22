package com.myssteriion.blindtest.rest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;

public class RestBuilderTest extends AbstractTest {

	@SuppressWarnings("unchecked")
	@Test
	public void create200() {
		
		ResponseEntity<?> re = RestBuilder.create200(null);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListDTO<ProfilDTO> body = (ListDTO<ProfilDTO>) re.getBody();
		Assert.assertTrue( body.getItems().isEmpty() );
	}
	
	@Test
	public void create204() {
		
		ResponseEntity<?> re = RestBuilder.create204();
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}

}
