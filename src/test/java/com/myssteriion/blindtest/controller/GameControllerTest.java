package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import com.myssteriion.blindtest.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class GameControllerTest extends AbstractTest {

	@Mock
	private GameService gameService;
	
	@InjectMocks
	private GameController gameController;



	@Test
	public void newGame() throws DaoException, NotFoundException, ConflictException {

		List<String> playersNames = Collections.singletonList("name");
		Mockito.when(gameService.newGame( Mockito.any(NewGameDTO.class) )).thenReturn(new GameDTO(playersNames, Duration.NORMAL));

		NewGameDTO newGameDto = new NewGameDTO(playersNames, Duration.NORMAL);

		ResponseEntity<GameDTO> re = gameController.newGame(newGameDto);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
	}

	@Test
	public void apply() throws DaoException, NotFoundException {

		List<String> playersNames = Collections.singletonList("name");
		Mockito.when(gameService.apply( Mockito.any(MusicResultDTO.class) )).thenReturn(new GameDTO(playersNames, Duration.NORMAL));
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		MusicResultDTO musicResultDto = new MusicResultDTO(0, musicDto, null, null, null, null, null);
		
		ResponseEntity<GameDTO> re = gameController.apply(musicResultDto);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( playersNames.size(), re.getBody().getPlayers().size() );
	}

}
