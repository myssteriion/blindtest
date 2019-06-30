package com.myssteriion.blindtest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.service.MusicService;

public class MusicControllerTest extends AbstractTest {

	@Mock
	private MusicService service;
	
	@InjectMocks
	private MusicController controller;
	
	
	
	@Test
	public void refresh() throws SqlException, AlreadyExistsException {

		Mockito.doNothing().when(service).refresh();

		ResponseEntity<Empty> re = controller.refresh();
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}
	
	@Test
	public void next() throws SqlException {
		
		MusicDTO dto = new MusicDTO("name", Theme.ANNEES_60);
		Mockito.when(service.next()).thenReturn(dto);
		
		ResponseEntity<MusicDTO> re = controller.next();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( dto, re.getBody() );
	}

}
