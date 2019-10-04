package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for GameDTO
 */
@CrossOrigin
@RestController
@RequestMapping(path = "game")
public class GameController {

	@Autowired
	private GameService gameService;


	/**
	 * Initialize the new game.
	 *
	 * @param newGameDto the NewGameDTO
	 * @return a GameDTO
	 * @throws DaoException      DB exception
	 * @throws NotFoundException NotFound exception
	 */
	@PostMapping
	public ResponseEntity<GameDTO> newGame(@RequestBody NewGameDTO newGameDto) throws DaoException, NotFoundException {

		GameDTO gameDto = gameService.newGame(newGameDto);
		return ResponseBuilder.create200(gameDto);
	}

	/**
	 * Apply the result on the game.
	 *
	 * @param musicResultDto the MusicResultDTO
	 * @return the GameDTO after apply
	 * @throws DaoException      DB exception
	 * @throws NotFoundException NotFound exception
	 */
	@PostMapping(path = "/apply")
	public ResponseEntity<GameDTO> apply(@RequestBody MusicResultDTO musicResultDto) throws DaoException, NotFoundException {

		GameDTO gameDTO = gameService.apply(musicResultDto);
		return ResponseBuilder.create200(gameDTO);
	}
	
}
