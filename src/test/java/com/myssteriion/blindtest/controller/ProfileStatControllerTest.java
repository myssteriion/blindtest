package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.ProfileStatService;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class ProfileStatControllerTest extends AbstractTest {

	@Mock
	private ProfileStatService profileStatService;
	
	@InjectMocks
	private ProfileStatController profileStatController;
	
	
	
	@Test
	public void findAllByProfilesIds() throws NotFoundException {

		NotFoundException nfe = new NotFoundException("nfe");
		Mockito.when(profileStatService.findByProfile(Mockito.any(ProfileDTO.class))).thenThrow(nfe).thenReturn(new ProfileStatDTO(0));

		try {
			profileStatController.findAllByProfilesIds(Arrays.asList(0));
			Assert.fail("Doit lever une CustomRuntimeException car le mock throw.");
		}
		catch (CustomRuntimeException e) {
			verifyException(new CustomRuntimeException("Can't find profile stat.", nfe), e);
		}
		
		ResponseEntity< Page<ProfileStatDTO> > re = profileStatController.findAllByProfilesIds(Arrays.asList(0));
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Page<ProfileStatDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getContent().size() );
	}

}
