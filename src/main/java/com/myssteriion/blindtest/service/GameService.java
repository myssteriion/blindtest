package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class GameService {

	@Autowired
	private MusicService musicService;

	@Autowired
	private ProfilService profilService;

	@Autowired
	private ProfilStatService profilStatService;

	private List<GameDTO> games = new ArrayList<>();
	


	public GameDTO newGame(NewGameDTO newGameDto) throws DaoException, NotFoundException, ConflictException {

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

			// update profilStatDto
			List<PlayerDTO> players = gameDto.getPlayers();
			List<String> winners = musicResultDto.getWinners();

			for (PlayerDTO playerDto : players) {

				ProfilDTO profilDto = new ProfilDTO(playerDto.getName());
				ProfilStatDTO profilStatDto = profilStatService.findByProfil(profilDto);
				profilStatDto.incrementListenedMusics( musicResultDto.getMusicDTO().getTheme() );

				if ( winners.stream().anyMatch(winnerName -> winnerName.equals(profilDto.getName())) )
					profilStatDto.incrementFoundMusics( musicResultDto.getMusicDTO().getTheme() );

				if (gameDto.getNbMusicsPlayed() == GameDTO.INIT)
					profilStatDto.incrementPlayedGames();
				else if ( gameDto.isLastNext() )
					profilStatDto.addBestScoreIfBetter( gameDto.getDuration(), playerDto.getScore() );

				profilStatService.update(profilStatDto);
			}

			gameDto.next();
		}

		return gameDto;
	}

	private void checkPlayers(List<String> playersNames) throws DaoException, NotFoundException, ConflictException {

		if ( playersNames.size() != new HashSet<>(playersNames).size() )
			throw new ConflictException("Player can appear only one time");

		for (String playerName : playersNames) {

			ProfilDTO profilDto =  profilService.find( new ProfilDTO(playerName));
			if (profilDto == null)
				throw new NotFoundException("Player '" + playerName + "' must have a profil");
		}
	}

}
