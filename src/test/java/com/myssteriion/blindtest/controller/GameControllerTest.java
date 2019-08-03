package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.common.NumMusic;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.GameResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
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
	public void newGame() throws SqlException, NotFoundException, AlreadyExistsException {

		List<PlayerDTO> players = Collections.singletonList(new PlayerDTO("name"));
		Mockito.when(gameService.newGame( Mockito.anyList() )).thenReturn(new GameDTO(players));

		ResponseEntity<GameDTO> re = gameController.newGame(players);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( players, re.getBody().getPlayers() );
	}

	@Test
	public void apply() throws SqlException, NotFoundException {

		List<PlayerDTO> players = Collections.singletonList(new PlayerDTO("name"));
		Mockito.when(gameService.apply( Mockito.any(GameResultDTO.class) )).thenReturn(new GameDTO(players));
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		GameResultDTO gameResultDto = new GameResultDTO(0, NumMusic.NORMAL, Round.CLASSIC, musicDto, null, null);
		
		ResponseEntity<GameDTO> re = gameController.apply(gameResultDto);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( players, re.getBody().getPlayers() );
	}

}
