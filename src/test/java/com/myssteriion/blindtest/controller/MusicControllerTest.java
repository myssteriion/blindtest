package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.spotify.exception.SpotifyException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;

public class MusicControllerTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@InjectMocks
	private MusicController musicController;
	
	
	
	@Test
	public void random() throws NotFoundException, IOException, SpotifyException {

		Flux fluxMock = Mockito.mock(Flux.class);
		Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60, ConnectionMode.OFFLINE).setFlux(fluxMock);
		Mockito.when(musicService.random(null, ConnectionMode.OFFLINE)).thenReturn(musicDto);
		Mockito.when(musicService.random(Collections.singletonList(Theme.ANNEES_60), ConnectionMode.OFFLINE)).thenReturn(musicDto);

		ResponseEntity<MusicDTO> re = musicController.random(null, ConnectionMode.OFFLINE);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );

		re = musicController.random(null, ConnectionMode.OFFLINE);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );

		re = musicController.random(Collections.singletonList(Theme.ANNEES_60), ConnectionMode.OFFLINE);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertEquals( musicDto, re.getBody() );
	}

}
