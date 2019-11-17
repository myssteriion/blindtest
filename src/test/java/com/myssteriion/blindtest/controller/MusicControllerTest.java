package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.MusicService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class MusicControllerTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@InjectMocks
	private MusicController musicController;
	
	
	
	@Test
	public void random() throws NotFoundException, IOException {

		Flux fluxMock = Mockito.mock(Flux.class);
		Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60).setFlux(fluxMock);
		Mockito.when(musicService.random(null)).thenReturn(musicDto);
		Mockito.when(musicService.random(Theme.ANNEES_60)).thenReturn(musicDto);

		ResponseEntity<MusicDTO> re = musicController.random(null);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );

		re = musicController.random(null);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );

		re = musicController.random(Theme.ANNEES_60);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );
	}

}
