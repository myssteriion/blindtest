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
import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.service.ProfilService;

public class ProfilControllerTest extends AbstractTest {

	@Mock
	private ProfilService profilService;
	
	@InjectMocks
	private ProfilController profilController;
	
	
	
	@Test
	public void save() throws SqlException, NotFoundException, AlreadyExistsException {
		
		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		Mockito.when(profilService.save(Mockito.any(ProfilDTO.class))).thenReturn(profilDto);
		
		ResponseEntity<ProfilDTO> actual = profilController.save(profilDto);
		Assert.assertEquals( HttpStatus.CREATED, actual.getStatusCode() );
		Assert.assertEquals( "name", actual.getBody().getName() );
		Assert.assertEquals( "avatar", actual.getBody().getAvatar() );
	}
	
	@Test
	public void update() throws SqlException, NotFoundException {
		
		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		Mockito.when(profilService.updated(Mockito.any(ProfilDTO.class))).thenReturn(profilDto);
		
		ResponseEntity<ProfilDTO> actual = profilController.update(1, profilDto);
		Assert.assertEquals( HttpStatus.OK, actual.getStatusCode() );
		Assert.assertEquals( "name", actual.getBody().getName() );
		Assert.assertEquals( "avatar", actual.getBody().getAvatar() );
	}
	
	@Test
	public void findAll() throws SqlException {

		IllegalArgumentException iae = new IllegalArgumentException("iae");
		Mockito.when(profilService.findAll()).thenThrow(iae).thenReturn( Arrays.asList( new ProfilDTO("name", "avatar") ) );

		try {
			profilController.findAll();
			Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
		}
		catch (IllegalArgumentException e) {
			verifyException(iae, e);
		}
		
		ResponseEntity< ListDTO<ProfilDTO> > re = profilController.findAll();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListDTO<ProfilDTO> actual = re.getBody();
		Assert.assertEquals( 1, actual.getItems().size() );		
	}

}
