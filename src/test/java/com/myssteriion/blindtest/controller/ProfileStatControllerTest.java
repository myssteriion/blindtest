package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.service.ProfileStatService;
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
	public void findAll() {

		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Page<ProfileStatDTO> pageMock = Mockito.mock(Page.class);
		Mockito.when(pageMock.getContent()).thenReturn(Arrays.asList(new ProfileStatDTO(1)));
		Mockito.when(profileStatService.findAll(Mockito.anyInt())).thenThrow(iae).thenReturn(pageMock);

		try {
			profileStatController.findAll(0);
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(iae, e);
		}
		
		ResponseEntity< Page<ProfileStatDTO> > re = profileStatController.findAll(0);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Page<ProfileStatDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getContent().size() );
	}

}
