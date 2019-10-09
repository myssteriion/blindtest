package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service for game.
 */
@Service
public class GameService {

	private MusicService musicService;

	private ProfileService profileService;

	private ProfileStatService profileStatService;

	/**
	 * The game list.
	 */
	private List<Game> games = new ArrayList<>();



	@Autowired
	public GameService(MusicService musicService, ProfileService profileService, ProfileStatService profileStatService) {
		this.musicService = musicService;
		this.profileService = profileService;
		this.profileStatService = profileStatService;
	}



	/**
	 * Create a game from new game.
	 *
	 * @param newGame the new game
	 * @return the game
	 * @throws NotFoundException the not found exception
	 */
	public Game newGame(NewGame newGame) throws NotFoundException {

		Tool.verifyValue("newGameDto", newGame);
		checkPlayers( newGame.getPlayersNames() );

		Game game = new Game( newGame.getPlayersNames(), newGame.getDuration() );
		game.setId( games.size() );
		games.add(game);

		musicService.refresh();

		return game;
	}

	/**
	 * Update the game from music result.
	 *
	 * @param musicResult the music result
	 * @return the game
	 * @throws NotFoundException the not found exception
	 */
	public Game apply(MusicResult musicResult) throws NotFoundException {

		Tool.verifyValue("musicResultDto", musicResult);
		Game game = games.stream()
							.filter( g -> g.getId().equals(musicResult.getGameId()) )
							.findFirst()
							.orElseThrow( () -> new NotFoundException("Game not found.") );

		if ( !game.isFinished() ) {

			// update musicDto
			MusicDTO musicDto = musicService.find( musicResult.getMusicDTO() );
			if (musicDto == null)
				throw new NotFoundException("Music not found.");

			musicDto.incrementPlayed();
			musicService.update(musicDto);

			// apply score
			game = game.getRoundContent().apply(game, musicResult);

			// update profileStatDto
			List<Player> players = game.getPlayers();
			List<String> winners = musicResult.getWinners();

			for (Player player : players) {

				ProfileDTO profileDto = profileService.find( new ProfileDTO(player.getName()) );
				ProfileStatDTO profileStatDto = profileStatService.findByProfile(profileDto);
				profileStatDto.incrementListenedMusics( musicResult.getMusicDTO().getTheme() );

				if ( winners.stream().anyMatch(winnerName -> winnerName.equals(profileDto.getName())) )
					profileStatDto.incrementFoundMusics( musicResult.getMusicDTO().getTheme() );

				if ( game.isFirstStep() )
					profileStatDto.incrementPlayedGames();
				else if ( game.isLastStep() )
					profileStatDto.addBestScoreIfBetter( game.getDuration(), player.getScore() );

				profileStatService.update(profileStatDto);
			}

			game.nextStep();
		}

		return game;
	}

	/**
	 * Test if all players have a profile.
	 *
	 * @param playersNames the players names
	 * @throws NotFoundException NotFound exception
	 */
	private void checkPlayers(Set<String> playersNames) throws NotFoundException {

		for (String playerName : playersNames) {

			ProfileDTO profileDto =  profileService.find( new ProfileDTO(playerName));
			if (profileDto == null)
				throw new NotFoundException("Player '" + playerName + "' must have a profile.");
		}
	}

}
