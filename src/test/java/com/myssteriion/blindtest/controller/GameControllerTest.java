package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.ConflictException;
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
	public void newGame() throws SqlException, NotFoundException, ConflictException {

		List<String> playersNames = Collections.singletonList("name");
		Mockito.when(gameService.newGame( Mockito.anyList() )).thenReturn(new GameDTO(playersNames));

		ResponseEntity<GameDTO> re = gameController.newGame(playersNames);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( playersNames.size(), re.getBody().getPlayers().size() );
	}

	@Test
	public void apply() throws SqlException, NotFoundException {

		List<String> playersNames = Collections.singletonList("name");
		Mockito.when(gameService.apply( Mockito.any(GameResultDTO.class) )).thenReturn(new GameDTO(playersNames));
		
		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_60);
		GameResultDTO gameResultDto = new GameResultDTO(0, NumMusic.NORMAL, Round.CLASSIC, musicDto, null, null);
		
		ResponseEntity<GameDTO> re = gameController.apply(gameResultDto);
		Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
		Assert.assertNotNull( re.getBody() );
		Assert.assertEquals( playersNames.size(), re.getBody().getPlayers().size() );
	}

}
