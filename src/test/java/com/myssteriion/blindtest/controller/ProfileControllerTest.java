package com.myssteriion.blindtest.controller;

import java.util.Arrays;

import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.ItemsPage;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.service.ProfileService;

public class ProfileControllerTest extends AbstractTest {

	@Mock
	private ProfileService profileService;
	
	@InjectMocks
	private ProfileController profileController;
	
	
	
	@Test
	public void save() throws DaoException, NotFoundException, ConflictException {
		
		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		Mockito.when(profileService.save(Mockito.any(ProfileDTO.class))).thenReturn(profileDto);
		
		ResponseEntity<ProfileDTO> actual = profileController.save(profileDto);
		Assert.assertEquals( HttpStatus.CREATED, actual.getStatusCode() );
		Assert.assertEquals( "name", actual.getBody().getName() );
		Assert.assertEquals( "avatar", actual.getBody().getAvatar().getName() );
	}
	
	@Test
	public void update() throws DaoException, NotFoundException, ConflictException {
		
		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		Mockito.when(profileService.update(Mockito.any(ProfileDTO.class))).thenReturn(profileDto);
		
		ResponseEntity<ProfileDTO> actual = profileController.update(1, profileDto);
		Assert.assertEquals( HttpStatus.OK, actual.getStatusCode() );
		Assert.assertEquals( "name", actual.getBody().getName() );
		Assert.assertEquals( "avatar", actual.getBody().getAvatar().getName() );
	}
	
	@Test
	public void findAll() throws DaoException {

		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Mockito.when(profileService.findAll()).thenThrow(iae).thenReturn( Arrays.asList( new ProfileDTO("name", new Avatar("avatar")) ) );

		try {
			profileController.findAll();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(iae, e);
		}

		ResponseEntity< ItemsPage<ProfileDTO> > re = profileController.findAll();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ItemsPage<ProfileDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getItems().size() );
	}

}
