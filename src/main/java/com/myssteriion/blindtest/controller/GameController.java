package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.rest.RestUtils;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for GameDTO.
 */
@CrossOrigin( origins = {"http://localhost:8085"} )
@RestController
@RequestMapping(path = "games")
public class GameController {

    private GameService gameService;



    /**
     * Instantiates a new Game controller.
     *
     * @param gameService the game service
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }



    /**
     * Initialize the new game.
     *
     * @param newGame the NewGameDTO
     * @return a GameDTO
     * @throws NotFoundException NotFound exception
     */
    @PostMapping
    public ResponseEntity<Game> newGame(@RequestBody NewGame newGame) throws NotFoundException, SpotifyException {

        Game game = gameService.newGame(newGame);
        return RestUtils.create200(game);
    }

    /**
     * Apply the result on the game.
     *
     * @param musicResult the MusicResultDTO
     * @return the GameDTO after apply
     * @throws NotFoundException NotFound exception
     * @throws ConflictException the conflict exception
     */
    @PostMapping(path = "/apply")
    public ResponseEntity<Game> apply(@RequestBody MusicResult musicResult) throws NotFoundException, ConflictException {

        Game game = gameService.apply(musicResult);
        return RestUtils.create200(game);
    }

    /**
     * Gets the game by id.
     *
     * @param id the id
     * @return a GameDTO
     * @throws NotFoundException NotFound exception
     */
    @GetMapping(path = CommonConstant.ID_PATH_PARAM)
    public ResponseEntity<Game> findById(@PathVariable(CommonConstant.ID) Integer id) throws NotFoundException {

        Game game = gameService.findById(id);
        return RestUtils.create200(game);
    }
    
    /**
     * Find all current games.
     *
     * @param pageNumber  the page number
     * @param itemPerPage the item per page
     * @return the pageable of games
     */
    @GetMapping()
    public ResponseEntity<Page<Game>> findAll(
            @RequestParam(value = CommonConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = CommonConstant.ITEM_PER_PAGE) Integer itemPerPage,
            @RequestParam(value = Constant.SHOW_FINISHED_GAMES, required = false, defaultValue = Constant.SHOW_FINISHED_GAMES_DEFAULT_VALUE) boolean showFinishedGames) {
    
        Page<Game> page = gameService.findAll(pageNumber, itemPerPage, showFinishedGames);
        return RestUtils.create200(page);
    }

}
