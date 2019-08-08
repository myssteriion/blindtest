package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameServiceTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@Mock
	private ProfilService profilService;
	
	@Mock
	private ProfilStatService profilStatService;
	
	@InjectMocks
	private GameService gameService;



	@Test
	public void newGame() throws SqlException, NotFoundException, ConflictException {

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		Mockito.when(profilService.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilDto);

		List<String> playersNames = Collections.singletonList("name");

		try {
			gameService.newGame(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'newGameDto' est obligatoire."), e);
		}

		try {
			gameService.newGame( new NewGameDTO(Arrays.asList("name", "name"), Duration.NORMAL) );
			Assert.fail("Doit lever une ConflictException car un param est KO.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("Player can be appear only one time"), e);
		}

		try {
			gameService.newGame( new NewGameDTO(playersNames, Duration.NORMAL) );
			Assert.fail("Doit lever une NotFoundException car un param est KO.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Player 'name' must had a profil"), e);
		}



		GameDTO game = gameService.newGame( new NewGameDTO(playersNames, Duration.NORMAL) );
		Assert.assertEquals( playersNames.size(), game.getPlayers().size() );
	}

	@Test
	public void apply() throws SqlException, NotFoundException, ConflictException {
		
		MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId(1);
		ProfilStatDTO profilStatDto = new ProfilStatDTO(1);

		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(null, musicDTO);
		Mockito.when(profilService.find( Mockito.any(ProfilDTO.class) )).thenReturn(profilDto);

		List<String> playersNames = Collections.singletonList("name");
		MusicResultDTO musicResultDto = new MusicResultDTO( 0, musicDTO, Arrays.asList(profilDto.getName()), null, null, Arrays.asList(profilDto.getName()) );


		try {
			gameService.apply(null);
			Assert.fail("Doit lever une IllegalArgumentException le gameDto n'est pas retrouvée.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicResultDto' est obligatoire."), e);
		}

		try {
			gameService.apply(musicResultDto);
			Assert.fail("Doit lever une NotFoundException le gameDto n'est pas retrouvée.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("gameDto not found."), e);
		}


		gameService.newGame( new NewGameDTO(playersNames, Duration.NORMAL) );


		try {
			gameService.apply(musicResultDto);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("musicDto not found"), e);
		}


		musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		profilDto = new ProfilDTO("name", "avatar");
		profilDto.setId(1);
		profilStatDto = new ProfilStatDTO(1);
		List<String> playersName = Collections.singletonList(profilDto.getName());
		musicResultDto = new MusicResultDTO( 0, musicDTO, playersName, null, null, null );

		// refaire les when car les objets ont subit un new
		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profilStatService.findByProfil( Mockito.any(ProfilDTO.class) )).thenReturn(profilStatDto);
		Mockito.when(profilStatService.update( Mockito.any(ProfilStatDTO.class) )).thenReturn(profilStatDto);


		GameDTO game = gameService.apply(musicResultDto);
		Assert.assertEquals( Round.getFirst(), game.getCurrent() );
		Assert.assertEquals( Round.getFirst().getNbPointWon(), game.getPlayers().get(0).getScore() );
		Assert.assertEquals( 1, game.getNbMusicsPlayed() );
		Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 1, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profilStatDto.getBestScore() );
		Assert.assertEquals( 1, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 1, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, null, null, null, playersName );
		game = gameService.apply(musicResultDto);
		Assert.assertEquals( Round.getFirst(), game.getCurrent() );
		Assert.assertEquals( Round.getFirst().getNbPointWon(), game.getPlayers().get(0).getScore() );
		Assert.assertEquals( 2, game.getNbMusicsPlayed() );
		Assert.assertEquals( 2, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 2, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profilStatDto.getBestScore() );
		Assert.assertEquals( 2, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 1, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, playersName, playersName, null, null );
		game = gameService.apply(musicResultDto);
		Assert.assertEquals( Round.getFirst(), game.getCurrent() );
		Assert.assertEquals( Round.getFirst().getNbPointWon()*2, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( 3, game.getNbMusicsPlayed() );
		Assert.assertEquals( 3, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 3, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profilStatDto.getBestScore() );
		Assert.assertEquals( 3, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 2, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, playersName, null, null, null );
		while (game.getCurrent() !=null)
			game = gameService.apply(musicResultDto);

		Assert.assertTrue( game.isFinished() );
	}

}
