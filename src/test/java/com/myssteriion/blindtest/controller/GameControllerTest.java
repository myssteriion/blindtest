package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameControllerTest extends AbstractTest {

	@Mock
	private GameService gameService;
	
	@InjectMocks
	private GameController gameController;



	@Test
	public void newGame() throws NotFoundException {

		List<String> playersNames = Collections.singletonList("name");
		Mockito.when(gameService.newGame( Mockito.any(NewGame.class) )).thenReturn(new Game(new HashSet<>(playersNames), Duration.NORMAL));

		NewGame newGame = new NewGame(new HashSet<>(playersNames), Duration.NORMAL);

		ResponseEntity<Game> re = gameController.newGame(newGame);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
	}

	@Test
	public void apply() throws NotFoundException, IOException {

		List<String> playersNames = Collections.singletonList("name");
		Mockito.when(gameService.apply( Mockito.any(MusicResult.class) )).thenReturn(new Game(new HashSet<>(playersNames), Duration.NORMAL));
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		MusicResult musicResult = new MusicResult(0, musicDto, null, null);
		
		ResponseEntity<Game> re = gameController.apply(musicResult);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( playersNames.size(), re.getBody().getPlayers().size() );
	}

}
