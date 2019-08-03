package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.ConflictException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.common.NumMusic;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.GameResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
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
		
		Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profilService.find( Mockito.any(ProfilDTO.class) )).thenReturn(profilDto, null, profilDto);
		Mockito.when(profilStatService.find( Mockito.any(ProfilStatDTO.class) )).thenReturn(null, profilStatDto);

		List<String> playersNames = Collections.singletonList("name");
		gameService.newGame(playersNames);

		try {
			gameService.apply(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'gameResultDto' est obligatoire."), e);
		}
		
		
		GameResultDTO gameResultDto = new GameResultDTO( 0, NumMusic.NORMAL, Round.CLASSIC, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );

		try {
			gameService.apply(gameResultDto);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilDto not found."), e);
		}
		
		try {
			gameService.apply(gameResultDto);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilStatDto not found."), e);
		}
		
		GameDTO game = gameService.apply(gameResultDto);
		Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );

		gameResultDto = new GameResultDTO( 0, NumMusic.FIRST, Round.CLASSIC, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );
		game = gameService.apply(gameResultDto);
		Assert.assertEquals( 200, game.getPlayers().get(0).getScore() );

		gameResultDto = new GameResultDTO( 0, NumMusic.LAST, Round.CLASSIC, musicDTO, Arrays.asList(profilDto), Arrays.asList(profilDto) );
		game = gameService.apply(gameResultDto);
		Assert.assertEquals( 300, game.getPlayers().get(0).getScore() );
	}

}
