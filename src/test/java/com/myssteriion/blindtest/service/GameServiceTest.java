package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
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
			verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
		}

		try {
			gameService.newGame(new ArrayList<>());
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
		}

		try {
			gameService.newGame( Arrays.asList("name", "name"));
			Assert.fail("Doit lever une ConflictException car un param est KO.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("player can be appear only one time"), e);
		}

		try {
			gameService.newGame(playersNames);
			Assert.fail("Doit lever une NotFoundException car un param est KO.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("player 'name' must had a profil"), e);
		}



		GameDTO game = gameService.newGame(playersNames);
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
		MusicResultDTO musicResultDto = new MusicResultDTO( 0, musicDTO, Arrays.asList(profilDto.getName()), null, Arrays.asList(profilDto.getName()) );


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


		gameService.newGame(playersNames);


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
		musicResultDto = new MusicResultDTO( 0, musicDTO, Arrays.asList(profilDto.getName()), null, null );

		// refaire les when car les objets ont subit un new
		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profilStatService.findByProfil( Mockito.any(ProfilDTO.class) )).thenReturn(profilStatDto);
		Mockito.when(profilStatService.update( Mockito.any(ProfilStatDTO.class) )).thenReturn(profilStatDto);


		GameDTO game = gameService.apply(musicResultDto);
		Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Round.CLASSIC, game.getCurrent() );
		Assert.assertEquals( 1, game.getNbMusicsPlayed() );
		Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 1, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profilStatDto.getBestScore() );
		Assert.assertEquals( 1, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 1, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, null, null, Arrays.asList(profilDto.getName()) );
		game = gameService.apply(musicResultDto);
		Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Round.CLASSIC, game.getCurrent() );
		Assert.assertEquals( 2, game.getNbMusicsPlayed() );
		Assert.assertEquals( 2, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 2, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profilStatDto.getBestScore() );
		Assert.assertEquals( 2, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 1, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, Arrays.asList(profilDto.getName()), null, null );
		for (int i = 2; i < Round.CLASSIC.getNbMusics(); i++)
			game = gameService.apply(musicResultDto);

		Assert.assertEquals( 1900, game.getPlayers().get(0).getScore() );
		Assert.assertNull( game.getCurrent() );
		Assert.assertEquals( 20, game.getNbMusicsPlayed() );
		Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 20, musicDTO.getPlayed() );
		Assert.assertEquals( 1900, profilStatDto.getBestScore() );
		Assert.assertEquals( 20, profilStatDto.getListenedMusics() );
		Assert.assertEquals( 19, profilStatDto.getFoundMusics() );
		Assert.assertEquals( 1, profilStatDto.getPlayedGames() );
	}

}
