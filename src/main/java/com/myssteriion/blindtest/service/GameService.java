package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.common.WinMode;
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

		Tool.verifyValue("newGame", newGame);

		Game game = new Game( cratePlayersList(newGame.getPlayersNames()), newGame.getDuration(), newGame.getThemes() );
		game.setId( games.size() );
		games.add(game);

		musicService.refresh();

		return game;
	}

	/**
	 * Create players list.
	 *
	 * @param playersNames the players names list
	 * @return the players list
	 * @throws NotFoundException the not found exception
	 */
	private Set<Player> cratePlayersList(Set<String> playersNames) throws NotFoundException {

		Set<Player> players = new HashSet<>();

		for (String playerName : playersNames) {

			ProfileDTO profile = profileService.find(new ProfileDTO(playerName));
			if (profile == null)
				throw new NotFoundException("Player '" + playerName + "' must have a profile.");

			players.add( new Player(profile) );
		}

		return players;
	}

	/**
	 * Update the game from music result.
	 *
	 * @param musicResult the music result
	 * @return the game
	 * @throws NotFoundException the not found exception
	 */
	public Game apply(MusicResult musicResult) throws NotFoundException {

		Tool.verifyValue("musicResult", musicResult);
		Game game = games.stream()
							.filter( g -> g.getId().equals(musicResult.getGameId()) )
							.findFirst()
							.orElseThrow( () -> new NotFoundException("Game not found.") );

		if ( !game.isFinished() ) {

			// update musicDto
			MusicDTO musicDto = musicService.find( musicResult.getMusic() );
			if (musicDto == null)
				throw new NotFoundException("Music not found.");

			musicDto.incrementPlayed();
			musicService.update(musicDto);

			// apply score
			game = game.getRoundContent().apply(game, musicResult);

			// update profileStatDto
			List<Player> players = game.getPlayers();

			updatePlayersRanks(players);

			for (Player player : players) {

				ProfileDTO profileDto = player.getProfile();
				ProfileStatDTO profileStatDto = profileStatService.findByProfile(profileDto);
				profileStatDto.incrementListenedMusics( musicResult.getMusic().getTheme() );

				if ( musicResult.isAuthorAndTitleWinner(profileDto.getName()) )
					profileStatDto.incrementFoundMusics( musicResult.getMusic().getTheme(), WinMode.BOTH );
				else if ( musicResult.isAuthorWinner(profileDto.getName()) )
					profileStatDto.incrementFoundMusics( musicResult.getMusic().getTheme(), WinMode.AUTHOR );
				else if ( musicResult.isTitleWinner(profileDto.getName()) )
					profileStatDto.incrementFoundMusics( musicResult.getMusic().getTheme(), WinMode.TITLE );


				if ( game.isFirstStep() )
					profileStatDto.incrementPlayedGames( game.getDuration() );
				else if ( game.isLastStep() ) {
					profileStatDto.addBestScoreIfBetter( game.getDuration(), player.getScore() );
					profileStatDto.incrementWonGames( player.getRank() );
				}

				profileStatService.update(profileStatDto);
			}

			game.nextStep();
		}

		return game;
	}

	/**
	 * Update players ranks.
	 *
	 * @param players the players
	 */
	private void updatePlayersRanks(List<Player> players) {

		List<Player> playersSorted = players.stream().sorted(Comparator.comparingInt(Player::getScore).reversed()).collect(Collectors.toList());

		Rank currentRank = Rank.FIRST;
		int jumpRank = 1;

		playersSorted.get(0).setRank(currentRank);
		int currentScore = playersSorted.get(0).getScore();

		for (int i = 1; i < playersSorted.size(); i++) {

			if (playersSorted.get(i).getScore() == currentScore) {
				jumpRank++;
			}
			else {
				for (int j = 0; j < jumpRank; j++)
					currentRank = currentRank.getNext();
				jumpRank = 1;
			}

			playersSorted.get(i).setRank(currentRank);
			currentScore = playersSorted.get(i).getScore();
		}
	}

	/**
	 * Find the game by id.
	 *
	 * @param id the id
	 * @return the game
	 * @throws NotFoundException the not found exception
	 */
	public Game findGame(Integer id) throws NotFoundException {

		Tool.verifyValue("id", id);

		return games.stream()
				.filter( game -> game.getId().equals(id) )
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Game not found."));
	}

}
