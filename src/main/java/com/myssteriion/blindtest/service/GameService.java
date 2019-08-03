package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.GameResultDTO;
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
	


	public GameDTO newGame(List<String> playersNames) throws SqlException, NotFoundException, ConflictException {

		Tool.verifyValue("playersNames", playersNames);
		checkPlayers(playersNames);

		GameDTO gameDto = new GameDTO(playersNames);
		gameDto.setId( games.size() );
		games.add(gameDto);
		return gameDto;
	}

	public GameDTO apply(GameResultDTO gameResultDto) throws SqlException, NotFoundException {

		Tool.verifyValue("gameResultDto", gameResultDto);
		GameDTO gameDto = games.stream()
							.filter( g -> g.getId().equals(gameResultDto.getGameId()) )
							.findFirst()
							.orElseThrow( () -> new NotFoundException("gameDto not found.") );


		MusicDTO musicDto = musicService.find( gameResultDto.getMusicDTO() );
		if (musicDto == null)
			throw new NotFoundException("musicDto not found");

		musicDto.incrementPlayed();
		musicService.update(musicDto);

		List<PlayerDTO> players = gameDto.getPlayers();
		List<String> winners = gameResultDto.getWinners();
		List<String> loosers = gameResultDto.getLoosers();

		for (PlayerDTO playerDto : players) {

			ProfilDTO profilDto = new ProfilDTO(playerDto.getName());
			ProfilStatDTO profilStatDto = findProfilStatDto(profilDto);
			profilStatDto.incrementListenedMusics();

			if ( winners.stream().anyMatch(winnerName -> winnerName.equals(profilDto.getName())) ) {
				profilStatDto.incrementFoundMusics();
				switch ( gameResultDto.getRound() ) {
					case CLASSIC : playerDto.addScore(100); break;
					default: new IllegalArgumentException("Il manque un case (" + gameResultDto.getRound() + ").");
				}
			}

			if ( loosers.stream().anyMatch(losserName -> losserName.equals(profilDto.getName())) ) {
				switch ( gameResultDto.getRound() ) {
					case CLASSIC : /* do nothing */ break;
					default: new IllegalArgumentException("Il manque un case (" + gameResultDto.getRound() + ").");
				}
			}

			switch ( gameResultDto.getNumMusic() ) {
				case FIRST: profilStatDto.incrementPlayedGames(); break;
				case LAST: profilStatDto.setBestScoreIfBetter( playerDto.getScore() ); break;
				default: new IllegalArgumentException("Il manque un case (" + gameResultDto.getRound() + ").");
			}

			profilStatService.update(profilStatDto);
		}

		return gameDto;
	}

	private ProfilStatDTO findProfilStatDto(ProfilDTO profilDto) throws SqlException, NotFoundException {

		ProfilDTO foundProfilDto = profilService.find(profilDto);

		if ( Tool.isNullOrEmpty(foundProfilDto) )
			throw new NotFoundException("profilDto not found.");

		ProfilStatDTO profilStatDTO = new ProfilStatDTO(foundProfilDto.getId());
		ProfilStatDTO foundProfilStatDTO = profilStatService.find(profilStatDTO);

		if ( Tool.isNullOrEmpty(foundProfilStatDTO) )
			throw new NotFoundException("profilStatDto not found.");

		return foundProfilStatDTO;
	}

	private void checkPlayers(List<String> playersNames) throws SqlException, NotFoundException, ConflictException {

		if ( playersNames.size() != new HashSet<>(playersNames).size() )
			throw new ConflictException("player can be appear only one time");

		for (String playerName : playersNames) {

			ProfilDTO profilDto =  profilService.find( new ProfilDTO(playerName));
			if (profilDto == null)
				throw new NotFoundException("player '" + playerName + "' must had a profil");
		}
	}

}
