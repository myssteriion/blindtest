package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

	@Autowired
	private MusicService musicService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private ProfileStatService profileStatService;

	private List<GameDTO> games = new ArrayList<>();
	


	public GameDTO newGame(NewGameDTO newGameDto) throws DaoException, NotFoundException {

		Tool.verifyValue("newGameDto", newGameDto);
		checkPlayers( newGameDto.getPlayersNames() );

		GameDTO gameDto = new GameDTO( newGameDto.getPlayersNames(), newGameDto.getDuration() );
		gameDto.setId( games.size() );
		games.add(gameDto);
		return gameDto;
	}

	public GameDTO apply(MusicResultDTO musicResultDto) throws DaoException, NotFoundException {

		Tool.verifyValue("musicResultDto", musicResultDto);
		GameDTO gameDto = games.stream()
							.filter( g -> g.getId().equals(musicResultDto.getGameId()) )
							.findFirst()
							.orElseThrow( () -> new NotFoundException("gameDto not found.") );

		if ( !gameDto.isFinished() ) {

			// update musicDto
			MusicDTO musicDto = musicService.find( musicResultDto.getMusicDTO() );
			if (musicDto == null)
				throw new NotFoundException("musicDto not found");

			musicDto.incrementPlayed();
			musicService.update(musicDto);

			// apply score
			gameDto = gameDto.getRoundContent().apply(gameDto, musicResultDto);

			// update profileStatDto
			List<PlayerDTO> players = gameDto.getPlayers();
			List<String> winners = musicResultDto.getWinners();

			for (PlayerDTO playerDto : players) {

				ProfileDTO profileDto = new ProfileDTO(playerDto.getName());
				ProfileStatDTO profileStatDto = profileStatService.findByProfile(profileDto);
				profileStatDto.incrementListenedMusics( musicResultDto.getMusicDTO().getTheme() );

				if ( winners.stream().anyMatch(winnerName -> winnerName.equals(profileDto.getName())) )
					profileStatDto.incrementFoundMusics( musicResultDto.getMusicDTO().getTheme() );

				if ( gameDto.isFirstStep() )
					profileStatDto.incrementPlayedGames();
				else if ( gameDto.isLastStep() )
					profileStatDto.addBestScoreIfBetter( gameDto.getDuration(), playerDto.getScore() );

				profileStatService.update(profileStatDto);
			}

			gameDto.nextStep();
		}

		return gameDto;
	}

	private void checkPlayers(Set<String> playersNames) throws DaoException, NotFoundException {

		for (String playerName : playersNames) {

			ProfileDTO profileDto =  profileService.find( new ProfileDTO(playerName));
			if (profileDto == null)
				throw new NotFoundException("Player '" + playerName + "' must have a profile");
		}
	}

}
