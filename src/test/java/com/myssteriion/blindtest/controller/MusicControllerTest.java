package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.rest.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.service.MusicService;

public class MusicControllerTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@InjectMocks
	private MusicController musicController;
	
	
	
	@Test
	public void refresh() throws DaoException, ConflictException {

		Mockito.doNothing().when(musicService).refresh();

		ResponseEntity<Empty> re = musicController.refresh();
		Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
	}
	
	@Test
	public void random() throws DaoException, NotFoundException {
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		Mockito.when(musicService.random()).thenReturn(musicDto);
		
		ResponseEntity<MusicDTO> re = musicController.random();
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );
	}

}
