package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.GameResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	


	public GameDTO newGame(List<PlayerDTO> players) throws SqlException, NotFoundException {

		Tool.verifyValue("players", players);

		for (PlayerDTO playerDto : players) {

			ProfilDTO profilDto =  profilService.find( new ProfilDTO(playerDto.getName(), ""));
			if (profilDto == null)
				throw new NotFoundException("player '" + playerDto.getName() + "' need match with a profil");
		}

		GameDTO gameDto = new GameDTO(players);
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


		gameResultDto.getMusicDTO().incrementPlayed();
		musicService.update( gameResultDto.getMusicDTO() );

		List<PlayerDTO> players = gameDto.getPlayers();
		List<ProfilDTO> winners = gameResultDto.getWinners();
		List<ProfilDTO> loosers = gameResultDto.getLoosers();

		for (PlayerDTO playerDto : players) {

			ProfilDTO profilDto = new ProfilDTO(playerDto.getName(), "");
			ProfilStatDTO profilStatDto = findProfilStatDto(profilDto);
			profilStatDto.incrementListenedMusics();

			if ( winners.stream().anyMatch(p -> p.getName().equals(profilDto.getName())) ) {
				profilStatDto.incrementFoundMusics();
				switch ( gameResultDto.getRound() ) {
					case CLASSIC : playerDto.addScore(100); break;
					default: new IllegalArgumentException("Il manque un case (" + gameResultDto.getRound() + ").");
				}
			}

			if ( loosers.stream().anyMatch(p -> p.getName().equals(profilDto.getName())) ) {
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

}
