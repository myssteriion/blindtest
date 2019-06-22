package com.myssteriion.blindtest.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.SqlException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.service.ProfilService;

public class ProfilControllerTest extends AbstractTest {

	@Mock
	private ProfilService service;
	
	@InjectMocks
	private ProfilController controller;
	
	
	
	@Test
	public void findAll() throws SqlException {

		List<ProfilDTO> list = Arrays.asList( new ProfilDTO("name", "avatar") );
		
		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Mockito.when(service.findAll()).thenThrow(iae).thenReturn(list);

		try {
			controller.findAll();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(iae, e);
		}
		ResponseEntity< ListDTO<ProfilDTO> > re = controller.findAll();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListDTO<ProfilDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getItems().size() );		
	}

}
