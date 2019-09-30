package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.NewGameDTO;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameServiceTest extends AbstractTest {

	@Mock
	private MusicService musicService;
	
	@Mock
	private ProfileService profileService;
	
	@Mock
	private ProfileStatService profileStatService;
	
	@InjectMocks
	private GameService gameService;



	@Test
	public void newGame() throws DaoException, NotFoundException {

		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		Mockito.when(profileService.find(Mockito.any(ProfileDTO.class))).thenReturn(null, profileDto);

		List<String> playersNames = Collections.singletonList("name");

		try {
			gameService.newGame(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'newGameDto' est obligatoire."), e);
		}

		try {
			gameService.newGame( new NewGameDTO(new HashSet<>(playersNames), Duration.NORMAL) );
			Assert.fail("Doit lever une NotFoundException car un param est KO.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Player 'name' must have a profile"), e);
		}



		GameDTO game = gameService.newGame( new NewGameDTO(new HashSet<>(playersNames), Duration.NORMAL) );
		Assert.assertEquals( playersNames.size(), game.getPlayers().size() );

		game = gameService.newGame( new NewGameDTO(new HashSet<>(Arrays.asList("name", "name")), Duration.NORMAL) );
		Assert.assertEquals( 1, game.getPlayers().size() );
	}

	@Test
	public void apply() throws DaoException, NotFoundException {
		
		MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		profileDto.setId(1);
		ProfileStatDTO profileStatDto = new ProfileStatDTO(1);

		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(null, musicDTO);
		Mockito.when(profileService.find( Mockito.any(ProfileDTO.class) )).thenReturn(profileDto);

		List<String> playersNames = Collections.singletonList("name");
		MusicResultDTO musicResultDto = new MusicResultDTO( 0, musicDTO, Arrays.asList(profileDto.getName()), Arrays.asList(profileDto.getName()) );


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


		gameService.newGame( new NewGameDTO(new HashSet<>(playersNames), Duration.NORMAL) );


		try {
			gameService.apply(musicResultDto);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("musicDto not found"), e);
		}


		musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		profileDto = new ProfileDTO("name", new Avatar("avatar"));
		profileDto.setId(1);
		profileStatDto = new ProfileStatDTO(1);
		List<String> playersName = Collections.singletonList(profileDto.getName());
		musicResultDto = new MusicResultDTO( 0, musicDTO, playersName, null );

		// refaire les when car les objets ont subit un new
		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profileStatService.findByProfile( Mockito.any(ProfileDTO.class) )).thenReturn(profileStatDto);
		Mockito.when(profileStatService.update( Mockito.any(ProfileStatDTO.class) )).thenReturn(profileStatDto);


		GameDTO game = gameService.apply(musicResultDto);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertTrue( 0 < game.getPlayers().get(0).getScore() );
		Assert.assertEquals( 1, game.getNbMusicsPlayed() );
		Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 1, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(1), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, null, playersName );
		game = gameService.apply(musicResultDto);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertTrue( 0 < game.getPlayers().get(0).getScore() );
		Assert.assertEquals( 2, game.getNbMusicsPlayed() );
		Assert.assertEquals( 2, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 2, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(2), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, playersName, null );
		game = gameService.apply(musicResultDto);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertTrue( 0 < game.getPlayers().get(0).getScore() );
		Assert.assertEquals( 3, game.getNbMusicsPlayed() );
		Assert.assertEquals( 3, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 3, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(3), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(2), profileStatDto.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( 1, profileStatDto.getPlayedGames() );

		musicResultDto = new MusicResultDTO( 0, musicDTO, playersName, null );
		while (game.getRound() != null)
			game = gameService.apply(musicResultDto);

		Assert.assertTrue( game.isFinished() );
		Assert.assertEquals( new Integer(4500), profileStatDto.getBestScores().get(Duration.NORMAL) );
	}

}
