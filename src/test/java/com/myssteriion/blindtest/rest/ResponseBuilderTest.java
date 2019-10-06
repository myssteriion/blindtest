package com.myssteriion.blindtest.rest;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ErrorMessage;
import com.myssteriion.blindtest.model.base.ItemsList;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseBuilderTest extends AbstractTest {

	@Test
	public void create200() {
		
		try {
			ResponseBuilder.create200( (ProfileDTO) null );
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'iModel' est obligatoire."), e);
		}
		
		ResponseEntity<ProfileDTO> re = ResponseBuilder.create200( new ProfileDTO("name", new Avatar("avatar")) );
		Assert.assertEquals( HttpStatus.OK , re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
	}
	
	@Test
	public void create200List() {
		
		ResponseEntity< ItemsList<ProfileDTO> > re = ResponseBuilder.create200( (List<ProfileDTO>) null );
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ItemsList<ProfileDTO> body = re.getBody();
		Assert.assertTrue( body.getItems().isEmpty() );
	}

	@Test
	public void create201() {

		try {
			ResponseBuilder.create201( (ProfileDTO) null );
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'iModel' est obligatoire."), e);
		}

		ResponseEntity<ProfileDTO> re = ResponseBuilder.create201( new ProfileDTO("name", new Avatar("avatar")) );
		Assert.assertEquals( HttpStatus.CREATED , re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
	}

	@Test
	public void create204() {
		
		ResponseEntity<?> re = ResponseBuilder.create204();
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}

	@Test
	public void create404() {
		
		ResponseBuilder rb = new ResponseBuilder();
		ResponseEntity<ErrorMessage> re = rb.create404( new IllegalArgumentException("iae") );
		Assert.assertEquals( HttpStatus.NOT_FOUND, re.getStatusCode() );
		Assert.assertEquals( HttpStatus.NOT_FOUND, re.getBody().getStatus() );
		Assert.assertEquals( "iae", re.getBody().getMessage() );
	}
	
	@Test
	public void create409() {
		
		ResponseBuilder rb = new ResponseBuilder();
		ResponseEntity<ErrorMessage> re = rb.create409( new IllegalArgumentException("iae") );
		Assert.assertEquals( HttpStatus.CONFLICT, re.getStatusCode() );
		Assert.assertEquals( HttpStatus.CONFLICT, re.getBody().getStatus() );
		Assert.assertEquals( "iae", re.getBody().getMessage() );
	}
	
	@Test
	public void create500() {
		
		ResponseBuilder rb = new ResponseBuilder();
		ResponseEntity<ErrorMessage> re = rb.create500( new IllegalArgumentException("iae") );
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode() );
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getBody().getStatus() );
		Assert.assertEquals( "iae", re.getBody().getMessage() );
	}
	
}
