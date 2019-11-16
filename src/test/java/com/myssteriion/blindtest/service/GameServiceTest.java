package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.WinMode;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
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
	public void newGame() throws NotFoundException {

		ProfileDTO profileDto = new ProfileDTO("name", "avatarName");
		ProfileDTO profileDto1 = new ProfileDTO("name1", "avatarName");
		Mockito.when(profileService.find(new ProfileDTO("name"))).thenReturn(null, profileDto);
		Mockito.when(profileService.find(new ProfileDTO("name1"))).thenReturn(profileDto1);

		List<String> playersNames = Arrays.asList("name", "name1");

		try {
			gameService.newGame(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'newGame' est obligatoire."), e);
		}

		try {
			gameService.newGame( new NewGame(new HashSet<>(playersNames), Duration.NORMAL) );
			Assert.fail("Doit lever une NotFoundException car un param est KO.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Player 'name' must have a profile."), e);
		}



		Game game = gameService.newGame( new NewGame(new HashSet<>(playersNames), Duration.NORMAL) );
		Assert.assertEquals( playersNames.size(), game.getPlayers().size() );

		game = gameService.newGame( new NewGame(new HashSet<>(Arrays.asList("name", "name1")), Duration.NORMAL) );
		Assert.assertEquals( 2, game.getPlayers().size() );
	}

	@Test
	public void apply() throws NotFoundException {

		MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		ProfileDTO profileDto = new ProfileDTO("name","avatarName").setId(1);
		ProfileDTO profileDto1 = new ProfileDTO("name1","avatarName").setId(2);
		ProfileDTO profileDto2 = new ProfileDTO("name2","avatarName").setId(3);
		ProfileStatDTO profileStatDto;
		ProfileStatDTO profileStatDto1;
		ProfileStatDTO profileStatDto2;

		Mockito.doNothing().when(musicService).refresh();
		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(null, musicDTO);
		Mockito.when(profileService.find( new ProfileDTO("name") )).thenReturn(profileDto);
		Mockito.when(profileService.find( new ProfileDTO("name1") )).thenReturn(profileDto1);
		Mockito.when(profileService.find( new ProfileDTO("name2") )).thenReturn(profileDto2);

		List<String> playersNames = Arrays.asList("name", "name1", "name2");
		MusicResult musicResult = new MusicResult( 0, musicDTO,
				Collections.singletonList(profileDto.getName()), null,
				Collections.singletonList(profileDto.getName()));


		try {
			gameService.apply(null);
			Assert.fail("Doit lever une IllegalArgumentException le gameDto n'est pas retrouvée.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
		}

		try {
			gameService.apply(musicResult);
			Assert.fail("Doit lever une NotFoundException le gameDto n'est pas retrouvée.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Game not found."), e);
		}


		gameService.newGame( new NewGame(new HashSet<>(playersNames), Duration.NORMAL) );


		try {
			gameService.apply(musicResult);
			Assert.fail("Doit lever une IllegalArgumentException car le mock return null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Music not found."), e);
		}


		musicDTO = new MusicDTO("name", Theme.ANNEES_60, 0);
		profileDto = new ProfileDTO("name", "avatarName").setId(1);
		profileDto1 = new ProfileDTO("name1","avatarName").setId(2);
		profileDto2 = new ProfileDTO("name2","avatarName").setId(3);
		profileStatDto = new ProfileStatDTO(1);
		profileStatDto1 = new ProfileStatDTO(2);
		profileStatDto2 = new ProfileStatDTO(3);

		// refaire les when car les objets ont subit un new
		Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(musicDTO);
		Mockito.when(profileStatService.findByProfile( new ProfileDTO("name") )).thenReturn(profileStatDto);
		Mockito.when(profileStatService.findByProfile( new ProfileDTO("name1") )).thenReturn(profileStatDto1);
		Mockito.when(profileStatService.findByProfile( new ProfileDTO("name2") )).thenReturn(profileStatDto2);
		Mockito.when(profileStatService.update( new ProfileStatDTO(1) )).thenReturn(profileStatDto);
		Mockito.when(profileStatService.update( new ProfileStatDTO(2) )).thenReturn(profileStatDto1);
		Mockito.when(profileStatService.update( new ProfileStatDTO(3) )).thenReturn(profileStatDto2);


		List<String> playersName = Collections.singletonList(profileDto.getName());
		musicResult = new MusicResult( 0, musicDTO, playersName, null, null );
		Game game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 1, game.getNbMusicsPlayed() );
		Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 1, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(1), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( 0, profileStatDto1.getBestScores().size() );
		Assert.assertEquals( new Integer(1), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertNull( profileStatDto1.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		musicResult = new MusicResult( 0, musicDTO, null, null, playersName );
		game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 2, game.getNbMusicsPlayed() );
		Assert.assertEquals( 2, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 2, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(2), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( new Integer(2), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertNull( profileStatDto1.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		musicResult = new MusicResult( 0, musicDTO, playersName, null, null );
		game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 200, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 3, game.getNbMusicsPlayed() );
		Assert.assertEquals( 3, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 3, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(3), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(2), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( new Integer(3), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertNull( profileStatDto1.getFoundMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		playersName = Collections.singletonList(profileDto1.getName());
		musicResult = new MusicResult( 0, musicDTO, playersName, null, null );
		game = gameService.apply(musicResult);
		game = gameService.apply(musicResult);
		game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 200, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.THIRD, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 6, game.getNbMusicsPlayed() );
		Assert.assertEquals( 6, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 6, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(6), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(2), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( new Integer(6), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto1.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		playersName = Collections.singletonList(profileDto.getName());
		musicResult = new MusicResult( 0, musicDTO, playersName, null, null );
		game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 300, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.THIRD, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 7, game.getNbMusicsPlayed() );
		Assert.assertEquals( 7, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 7, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(7), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( new Integer(7), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto1.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		playersName = Collections.singletonList(profileDto.getName());
		musicResult = new MusicResult( 0, musicDTO, null, playersName, null );
		game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 400, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.THIRD, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 8, game.getNbMusicsPlayed() );
		Assert.assertEquals( 8, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 8, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(8), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( new Integer(8), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto1.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		playersName = Collections.singletonList(profileDto.getName());
		musicResult = new MusicResult( 0, musicDTO, playersName, playersName, null );
		game = gameService.apply(musicResult);
		Assert.assertEquals( Round.CLASSIC, game.getRound() );
		Assert.assertEquals( 600, game.getPlayers().get(0).getScore() );
		Assert.assertEquals( Rank.FIRST, game.getPlayers().get(0).getRank() );
		Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
		Assert.assertEquals( Rank.SECOND, game.getPlayers().get(1).getRank() );
		Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
		Assert.assertEquals( Rank.THIRD, game.getPlayers().get(2).getRank() );
		Assert.assertEquals( 9, game.getNbMusicsPlayed() );
		Assert.assertEquals( 9, game.getNbMusicsPlayedInRound() );
		Assert.assertEquals( 9, musicDTO.getPlayed() );
		Assert.assertEquals( 0, profileStatDto.getBestScores().size() );
		Assert.assertEquals( new Integer(9), profileStatDto.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto.getPlayedGames().get(game.getDuration()) );
		Assert.assertEquals( new Integer(9), profileStatDto1.getListenedMusics().get(Theme.ANNEES_60) );
		Assert.assertEquals( new Integer(3), profileStatDto1.getFoundMusics().get(Theme.ANNEES_60).get(WinMode.AUTHOR) );
		Assert.assertEquals( new Integer(1), profileStatDto1.getPlayedGames().get(game.getDuration()) );

		musicResult = new MusicResult( 0, musicDTO, playersName, null, null );
		while (game.getRound() != null)
			game = gameService.apply(musicResult);

		Assert.assertTrue( game.isFinished() );
		// classic : 15 à 100pts / 1 à 200pts (15 car 20 - 1 loser - 3 ou profile1 gagne - 1 ou profile gagne auteur et titre)
		// choice : 4 à 150pts / 8 à 100 pts
		// thief : 20 à 100pts
		Assert.assertEquals( new Integer(15*100 + 1*200 + 4*150 + 8*100 + 20*100), profileStatDto.getBestScores().get(Duration.NORMAL) );
	}

	@Test
	public void findGame() throws NotFoundException {

		try {
			gameService.findGame(null);
			Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'id' est obligatoire."), e);
		}


		try {
			gameService.findGame(10);
			Assert.fail("Doit lever une NotFoundException car le dto n'existe pas.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Game not found."), e);
		}


		ProfileDTO profileDto = new ProfileDTO("name", "avatarName");
		ProfileDTO profileDto1 = new ProfileDTO("name1", "avatarName");
		Mockito.when(profileService.find(new ProfileDTO("name"))).thenReturn(profileDto);
		Mockito.when(profileService.find(new ProfileDTO("name1"))).thenReturn(profileDto1);

		NewGame ng = new NewGame(new HashSet<>(Arrays.asList("name", "name1")), Duration.NORMAL);
		Game expected = gameService.newGame(ng);

		Game actual = gameService.findGame(expected.getId());
		Assert.assertSame(expected, actual);
	}

}
