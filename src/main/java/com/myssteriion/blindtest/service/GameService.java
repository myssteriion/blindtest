package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
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
	


	public GameDTO newGame(NewGameDTO newGameDto) throws SqlException, NotFoundException, ConflictException {

		Tool.verifyValue("newGameDto", newGameDto);
		checkPlayers( newGameDto.getPlayersNames() );

		GameDTO gameDto = new GameDTO( newGameDto.getPlayersNames(), newGameDto.getDuration() );
		gameDto.setId( games.size() );
		games.add(gameDto);
		return gameDto;
	}

	public GameDTO apply(MusicResultDTO musicResultDto) throws SqlException, NotFoundException {

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

			// update profilStatDto
			List<PlayerDTO> players = gameDto.getPlayers();
			List<String> winners = musicResultDto.getWinners();

			for (PlayerDTO playerDto : players) {

				ProfilDTO profilDto = new ProfilDTO(playerDto.getName());
				ProfilStatDTO profilStatDto = profilStatService.findByProfil(profilDto);
				profilStatDto.incrementListenedMusics();

				if ( winners.stream().anyMatch(winnerName -> winnerName.equals(profilDto.getName())) )
					profilStatDto.incrementFoundMusics();

				if (gameDto.getNbMusicsPlayed() == GameDTO.FIRST_MUSIC)
					profilStatDto.incrementPlayedGames();
				else if ( gameDto.isLastNext() )
					profilStatDto.setBestScoreIfBetter( playerDto.getScore() );

				profilStatService.update(profilStatDto);
			}

			gameDto = gameDto.getRoundContent().apply(gameDto, musicResultDto);

			gameDto.next();
		}

		return gameDto;
	}

	private void checkPlayers(List<String> playersNames) throws SqlException, NotFoundException, ConflictException {

		if ( playersNames.size() != new HashSet<>(playersNames).size() )
			throw new ConflictException("Player can be appear only one time");

		for (String playerName : playersNames) {

			ProfilDTO profilDto =  profilService.find( new ProfilDTO(playerName));
			if (profilDto == null)
				throw new NotFoundException("Player '" + playerName + "' must had a profil");
		}
	}

}
