package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class ProfileControllerTest extends AbstractTest {

	@Mock
	private ProfileService profileService;
	
	@InjectMocks
	private ProfileController profileController;
	
	
	
	@Test
	public void save() throws ConflictException {
		
		ProfileDTO profileDto = new ProfileDTO("name", "avatar");
		Mockito.when(profileService.save(Mockito.any(ProfileDTO.class))).thenReturn(profileDto);
		
		ResponseEntity<ProfileDTO> actual = profileController.save(profileDto);
		Assert.assertEquals( HttpStatus.CREATED, actual.getStatusCode() );
		Assert.assertEquals( "name", actual.getBody().getName() );
		Assert.assertEquals( "avatar", actual.getBody().getAvatarName() );
	}
	
	@Test
	public void update() throws NotFoundException, ConflictException {
		
		ProfileDTO profileDto = new ProfileDTO("name", "avatar");
		Mockito.when(profileService.update(Mockito.any(ProfileDTO.class))).thenReturn(profileDto);
		
		ResponseEntity<ProfileDTO> actual = profileController.update(1, profileDto);
		Assert.assertEquals( HttpStatus.OK, actual.getStatusCode() );
		Assert.assertEquals( "name", actual.getBody().getName() );
		Assert.assertEquals( "avatar", actual.getBody().getAvatarName() );
	}
	
	@Test
	public void findAllByNameStartingWith() {

		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Page<ProfileDTO> pageMock = Mockito.mock(Page.class);
		Mockito.when(pageMock.getContent()).thenReturn(Arrays.asList(new ProfileDTO("name", "avatar")));
		Mockito.when(profileService.findAllByNameStartingWith(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(iae).thenReturn(pageMock);

		try {
			profileController.findAllByNameStartingWith("", 0, 1);
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			TestUtils.verifyException(iae, e);
		}

		ResponseEntity< Page<ProfileDTO> > re = profileController.findAllByNameStartingWith("", 0, 1);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Page<ProfileDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getContent().size() );
	}

	@Test
	public void delete() throws NotFoundException {

		Mockito.doNothing().when(profileService).delete(Mockito.any(ProfileDTO.class));

		ResponseEntity<Empty> actual = profileController.delete(1);
		Assert.assertEquals( HttpStatus.NO_CONTENT, actual.getStatusCode() );
	}

}
