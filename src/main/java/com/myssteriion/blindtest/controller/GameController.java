package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.GameResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public ResponseEntity<GameDTO> newGame(@RequestBody List<PlayerDTO> players) {

		GameDTO gameDTO = gameService.newGame(players);
		return ResponseBuilder.create200(gameDTO);
	}

	@RequestMapping(
		method = RequestMethod.POST,
		path = "/apply"
	)
	public ResponseEntity<GameDTO> apply(@RequestBody GameResultDTO gameResultDto) throws SqlException, NotFoundException {

		GameDTO gameDTO = gameService.apply(gameResultDto);
		return ResponseBuilder.create200(gameDTO);
	}
	
}
