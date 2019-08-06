package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(
	path = "game"
)
public class GameController {

	@Autowired
	private GameService gameService;
	
	
	
	@RequestMapping(
		method = RequestMethod.POST
	)
	public ResponseEntity<GameDTO> newGame(@RequestBody NewGameDTO newGameDto) throws SqlException, NotFoundException, ConflictException {

		GameDTO gameDto = gameService.newGame(newGameDto);
		return ResponseBuilder.create200(gameDto);
	}

	@RequestMapping(
		method = RequestMethod.POST,
		path = "/apply"
	)
	public ResponseEntity<GameDTO> apply(@RequestBody MusicResultDTO musicResultDto) throws SqlException, NotFoundException {

		GameDTO gameDTO = gameService.apply(musicResultDto);
		return ResponseBuilder.create200(gameDTO);
	}
	
}
