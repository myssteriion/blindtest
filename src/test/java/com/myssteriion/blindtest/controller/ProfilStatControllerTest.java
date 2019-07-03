package com.myssteriion.blindtest.controller;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.service.ProfilStatService;

public class ProfilStatControllerTest extends AbstractTest {

	@Mock
	private ProfilStatService profilStatService;
	
	@InjectMocks
	private ProfilStatController profilStatController;
	
	
	
	@Test
	public void findAll() throws SqlException {

		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Mockito.when(profilStatService.findAll()).thenThrow(iae).thenReturn( Arrays.asList( new ProfilStatDTO(1) ) );

		try {
			profilStatController.findAll();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(iae, e);
		}
		
		ResponseEntity< ListDTO<ProfilStatDTO> > re = profilStatController.findAll();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListDTO<ProfilStatDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getItems().size() );		
	}

}
