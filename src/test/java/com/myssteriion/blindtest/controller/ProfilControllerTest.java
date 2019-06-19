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
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.base.ErrorModel;
import com.myssteriion.blindtest.model.base.ListModel;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.service.ProfilService;

public class ProfilControllerTest extends AbstractTest {

	@Mock
	private ProfilService service;
	
	@InjectMocks
	private ProfilController controller;
	
	
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void findAll() throws EntityManagerException {

		List<ProfilDTO> list = Arrays.asList( new ProfilDTO("name", "avatar") );
		
		NullPointerException npe = new NullPointerException("npe");
		Mockito.when(service.findAll()).thenThrow(npe).thenReturn(list);

		ResponseEntity re = controller.findAll();
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode() );
		Assert.assertEquals( "Can't find all profil.", ((ErrorModel) re.getBody()).getMessage() );
		Assert.assertEquals( "npe", ((ErrorModel) re.getBody()).getCauses().get(0) );
		
		re = controller.findAll();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		ListModel<ProfilDTO> actual = (ListModel<ProfilDTO>) re.getBody();
		Assert.assertEquals( 1, actual.getItems().size() );		
	}

}
