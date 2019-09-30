package com.myssteriion.blindtest.controller;

import java.util.Arrays;

import com.myssteriion.blindtest.db.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.service.ProfileStatService;

public class ProfileStatControllerTest extends AbstractTest {

	@Mock
	private ProfileStatService profileStatService;
	
	@InjectMocks
	private ProfileStatController profileStatController;
	
	
	
	@Test
	public void findAll() throws DaoException {

		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Mockito.when(profileStatService.findAll()).thenThrow(iae).thenReturn( Arrays.asList( new ProfileStatDTO(1) ) );

		try {
			profileStatController.findAll();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(iae, e);
		}
		
		ResponseEntity< ListDTO<ProfileStatDTO> > re = profileStatController.findAll();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListDTO<ProfileStatDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getItems().size() );		
	}

}
