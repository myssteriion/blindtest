package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.game.Game;
import com.myssteriion.blindtest.model.dto.game.MusicResult;
import com.myssteriion.blindtest.model.dto.game.NewGame;
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
	 * @param newGame the NewGameDTO
	 * @return a GameDTO
	 * @throws DaoException      DB exception
	 * @throws NotFoundException NotFound exception
	 */
	@PostMapping
	public ResponseEntity<Game> newGame(@RequestBody NewGame newGame) throws DaoException, NotFoundException {

		Game game = gameService.newGame(newGame);
		return ResponseBuilder.create200(game);
	}

	/**
	 * Apply the result on the game.
	 *
	 * @param musicResult the MusicResultDTO
	 * @return the GameDTO after apply
	 * @throws DaoException      DB exception
	 * @throws NotFoundException NotFound exception
	 */
	@PostMapping(path = "/apply")
	public ResponseEntity<Game> apply(@RequestBody MusicResult musicResult) throws DaoException, NotFoundException {

		Game game = gameService.apply(musicResult);
		return ResponseBuilder.create200(game);
	}
	
}
