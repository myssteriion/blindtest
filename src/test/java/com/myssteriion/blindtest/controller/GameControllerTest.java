package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GameMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.spotify.exception.SpotifyException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameControllerTest extends AbstractTest {

	@Mock
	private GameService gameService;
	
	@InjectMocks
	private GameController gameController;



	@Test
	public void newGame() throws NotFoundException, SpotifyException {

		String playerName = "name";
		List<Player> players = Arrays.asList(
				new Player(new ProfileDTO("name")),
				new Player(new ProfileDTO("name1")));
		Mockito.when(gameService.newGame( Mockito.any(NewGame.class) )).thenReturn(new Game(new HashSet<>(players), Duration.NORMAL, null, GameMode.OFFLINE));

		NewGame newGame = new NewGame(new HashSet<>(Collections.singletonList(playerName)), Duration.NORMAL, null, GameMode.OFFLINE);

		ResponseEntity<Game> re = gameController.newGame(newGame);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
	}

	@Test
	public void apply() throws NotFoundException {

		List<Player> players = Arrays.asList(
				new Player(new ProfileDTO("name")),
				new Player(new ProfileDTO("name1")));
		Mockito.when(gameService.apply( Mockito.any(MusicResult.class) )).thenReturn(new Game(new HashSet<>(players), Duration.NORMAL, null, GameMode.OFFLINE));
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		MusicResult musicResult = new MusicResult(0, musicDto, null, null, null);

		ResponseEntity<Game> re = gameController.apply(musicResult);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( players.size(), re.getBody().getPlayers().size() );
	}

	@Test
	public void findById() throws NotFoundException {

		List<Player> players = Arrays.asList(
				new Player(new ProfileDTO("name")),
				new Player(new ProfileDTO("name1")));

		Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, GameMode.OFFLINE);
		game.setId(11);
		Mockito.when(gameService.findGame( Mockito.anyInt())).thenReturn(game);

		ResponseEntity<Game> re = gameController.findById(game.getId());
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertSame( game, re.getBody() );
	}

}
