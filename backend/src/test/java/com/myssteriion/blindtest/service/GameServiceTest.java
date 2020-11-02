package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.entity.ProfileStatEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.model.round.impl.Classic;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GameServiceTest extends AbstractTest {
    
    @Mock
    private MusicService musicService;
    
    @Mock
    private ProfileService profileService;
    
    @Autowired
    private RoundService roundService;
    
    private GameService gameService;
    
    
    
    @BeforeEach
    void before() {
        gameService = new GameService(musicService, profileService, roundService);
    }
    
    
    
    @Test
    void newGame() throws NotFoundException {
        
        Mockito.when(profileService.find( Mockito.any(ProfileEntity.class) )).thenReturn(null).thenAnswer(invocation -> {
            ProfileEntity p = invocation.getArgument(0);
            return new ProfileEntity()
                    .setId( p.getId() )
                    .setName( "name" + p.getId() );
        });
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class))).thenReturn(0, 10);
        
        Set<Integer> profilesId = new HashSet<>( Arrays.asList(0, 1) );
        
        TestUtils.assertThrowMandatoryField( "newGame", () -> gameService.newGame(null) );
        
        NewGame newGame = new NewGame();
        TestUtils.assertThrowMandatoryField( "newGame -> players", () -> gameService.newGame(newGame) );
        
        newGame.setProfilesId(profilesId);
        TestUtils.assertThrowMandatoryField( "newGame -> duration", () -> gameService.newGame(newGame) );
        
        newGame.setDuration(Duration.NORMAL);
        TestUtils.assertThrow( NotFoundException.class, "Zero music found ('ANNEES_60').",
                () -> gameService.newGame(newGame.setThemes(Collections.singletonList(Theme.ANNEES_60)) ) );
    
        TestUtils.assertThrow( NotFoundException.class, "Profile '0' not found.", () -> gameService.newGame(newGame) );
        
        newGame.setProfilesId(new HashSet<>(Collections.singletonList(1)));
        TestUtils.assertThrow( IllegalArgumentException.class, "2 players at minimum.", () -> gameService.newGame(newGame) );
        
        newGame.setProfilesId(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)));
        TestUtils.assertThrow( IllegalArgumentException.class, "16 players at maximum.", () -> gameService.newGame(newGame) );
        
        Game game = gameService.newGame( new NewGame().setProfilesId(profilesId).setDuration(Duration.NORMAL) );
        Assertions.assertEquals( profilesId.size(), game.getPlayers().size() );
        
        game = gameService.newGame( new NewGame().setProfilesId(new HashSet<>(Arrays.asList(0, 1))).setDuration(Duration.NORMAL) );
        Assertions.assertEquals( 2, game.getPlayers().size() );
    }
    
    @Test
    void apply() throws NotFoundException, ConflictException {
        
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_60);
        MusicEntity music2 = new MusicEntity("name", Theme.ANNEES_80);
        
        ProfileStatEntity profileStat = new ProfileStatEntity();
        ProfileStatEntity profileStat1 = new ProfileStatEntity();
        ProfileStatEntity profileStat2 = new ProfileStatEntity();
        ProfileEntity profile = new ProfileEntity().setName("name").setId(1).setProfileStat(profileStat);
        ProfileEntity profile1 = new ProfileEntity().setName("name1").setId(2).setProfileStat(profileStat1);
        ProfileEntity profile2 = new ProfileEntity().setName("name2").setId(3).setProfileStat(profileStat2);
        
        Mockito.when(musicService.find( Mockito.any(MusicEntity.class) )).thenReturn(null, music2, music);
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class))).thenReturn(10);
        
        ProfileEntity finalProfile = profile;
        ProfileEntity finalProfile1 = profile1;
        ProfileEntity finalProfile2 = profile2;
        Mockito.when(profileService.find( Mockito.any(ProfileEntity.class) )).thenAnswer(invocation -> {
            ProfileEntity p = invocation.getArgument(0);
            switch ( p.getId() ) {
                case 1: return finalProfile;
                case 2: return finalProfile1;
                default: return finalProfile2;
            }
        });
        
        Set<Integer> profilesId = new HashSet<>( Arrays.asList(0, 1, 2) );
        MusicResult musicResult = new MusicResult().setGameId(0).setMusic(music)
                .setAuthorWinners(Collections.singletonList(profile.getName()))
                .setLosers(Collections.singletonList(profile.getName()));
        
        TestUtils.assertThrowMandatoryField( "musicResult", () -> gameService.apply(null) );
        TestUtils.assertThrowMandatoryField( "musicResult -> gameId", () -> gameService.apply(new MusicResult()) );
        
        TestUtils.assertThrow( NotFoundException.class, "Game not found.", () -> gameService.apply(musicResult) );
        
        gameService.newGame( new NewGame().setProfilesId(profilesId).setDuration(Duration.NORMAL)
                .setThemes(Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70)) );
        
        TestUtils.assertThrow( NotFoundException.class, "Music not found.", () -> gameService.apply(musicResult) );
        
        musicResult.setGameId(0).setMusic(music2).setAuthorWinners(Collections.singletonList(profile.getName()))
                .setLosers(Collections.singletonList(profile.getName()));
        TestUtils.assertThrow( NotFoundException.class, "'ANNEES_80' not found for this game ([ANNEES_60, ANNEES_70]).",
                () -> gameService.apply(musicResult) );
        
        music = new MusicEntity("name", Theme.ANNEES_60);
        profile = new ProfileEntity().setName("name").setId(1).setProfileStat(profileStat);
        profile1 = new ProfileEntity().setName("name1").setId(2).setProfileStat(profileStat1);
        profile2 = new ProfileEntity().setName("name2").setId(3).setProfileStat(profileStat2);
        
        
        // refaire les when car les objets ont subit un new
        Mockito.when(musicService.find( Mockito.any(MusicEntity.class) )).thenReturn(music);
        Mockito.when(musicService.update( Mockito.any(MusicEntity.class) )).thenReturn(music);
        
        
        List<String> playersName = Collections.singletonList(profile.getName());
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(playersName).setTitleWinners(null).setLosers(null).setPenalties(null);
        Game game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 100, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(1), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(1).getScore() );
        Assertions.assertNull( game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 2, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 1, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 1, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(1), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 1, music.getPlayed() );
        Assertions.assertNull( profile.getProfileStat().getBestScores() );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertNull( profileStat1.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(1), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertNull( profileStat1.getFoundMusics() );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(null).setTitleWinners(null).setLosers(playersName).setPenalties(null);
        game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 100, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(1), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(1).getScore() );
        Assertions.assertNull( game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 2, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 2, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 2, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(2), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 2, music.getPlayed() );
        Assertions.assertNull( profileStat.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertEquals( Integer.valueOf(2), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertNull( profileStat1.getFoundMusics() );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(playersName).setTitleWinners(null).setLosers(null).setPenalties(null);
        game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 200, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(2), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(1).getScore() );
        Assertions.assertNull( game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 2, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 3, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 3, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(3), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 3, music.getPlayed() );
        Assertions.assertNull( profileStat.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(3), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertEquals( Integer.valueOf(3), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertNull( profileStat1.getFoundMusics() );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        playersName = Collections.singletonList(profile1.getName());
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(playersName).setTitleWinners(null).setLosers(null).setPenalties(null);
        game = gameService.apply(musicResult);
        game = gameService.apply(musicResult);
        game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 200, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(2), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 2, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 6, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 6, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(6), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 6, music.getPlayed() );
        Assertions.assertNull( profileStat.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(6), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertEquals( Integer.valueOf(6), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        playersName = Collections.singletonList(profile.getName());
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(playersName).setTitleWinners(null).setLosers(null).setPenalties(null);
        game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 300, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 7, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 7, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(7), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 7, music.getPlayed() );
        Assertions.assertNull( profileStat.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(7), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertEquals( Integer.valueOf(7), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        playersName = Collections.singletonList(profile.getName());
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(null).setTitleWinners(playersName).setLosers(null).setPenalties(null);
        game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 400, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 8, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 8, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(8), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 8, music.getPlayed() );
        Assertions.assertNull( profileStat.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(8), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertEquals( Integer.valueOf(8), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        playersName = Collections.singletonList(profile.getName());
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(playersName).setTitleWinners(playersName).setLosers(null).setPenalties(null);
        game = gameService.apply(musicResult);
        Assertions.assertEquals( Classic.class, game.getRound().getClass() );
        Assertions.assertEquals( 600, game.getPlayers().get(0).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assertions.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assertions.assertEquals( Integer.valueOf(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assertions.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assertions.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assertions.assertEquals( 9, game.getNbMusicsPlayed() );
        Assertions.assertEquals( 9, game.getNbMusicsPlayedInRound() );
        Assertions.assertEquals( Integer.valueOf(9), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( 9, music.getPlayed() );
        Assertions.assertNull( profileStat.getBestScores() );
        Assertions.assertEquals( Integer.valueOf(9), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat.getPlayedGames() );
        Assertions.assertEquals( Integer.valueOf(9), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assertions.assertNull( profileStat1.getPlayedGames() );
        
        musicResult.setGameId(0).setMusic(music).setAuthorWinners(playersName).setTitleWinners(null).setLosers(null).setPenalties(null);
        while (game.getRound() != null)
            game = gameService.apply(musicResult);
        
        Assertions.assertTrue( game.isFinished() );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(game.getDuration()) );
        Assertions.assertEquals( Integer.valueOf(1), profileStat1.getPlayedGames().get(game.getDuration()) );
        
        // classic : 15 à 100pts / 1 à 200pts (15 car 20 - 1 loser - 3 ou profile1 gagne - 1 ou profile gagne auteur et titre)
        // choice : 4 à 150pts / 8 à 100 pts
        // lucky : 10 à 150pts / 10 à 100 pts (le 10*100 c'est aléatoire)
        // friendship : 10 à 150pts
        // thief : 20 à 100pts
        // recovery : 10 à 30pts
        Assertions.assertTrue( profileStat.getBestScores().get(Duration.NORMAL) >= Integer.valueOf(15*100 + 1*200 + 4*150 + 8*100 + 10*150 + 10*150 + 20*100 + 10*30)
                && profileStat.getBestScores().get(Duration.NORMAL) <= Integer.valueOf(15*100 + 1*200 + 4*150 + 8*100 + 10*150 + 10*100 + 10*150 + 20*100 + 10*30) );
    
        game = gameService.apply(musicResult);
        Assertions.assertTrue( game.isFinished() );
    }
    
    @Test
    void findById() throws NotFoundException {
        
        TestUtils.assertThrowMandatoryField("id", () -> gameService.findById(null) );
        
        TestUtils.assertThrow( NotFoundException.class, "Game not found.", () -> gameService.findById(10) );
        
        ProfileEntity profile = new ProfileEntity().setName("name").setId(1);
        ProfileEntity profile1 = new ProfileEntity().setName("name1").setId(2);
        Mockito.when(profileService.find( Mockito.any(ProfileEntity.class) )).thenAnswer(invocation -> {
            ProfileEntity p = invocation.getArgument(0);
            return ( p.getId().equals(1) ) ? profile : profile1;
        });
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class))).thenReturn(10);
        
        NewGame ng = new NewGame().setProfilesId(new HashSet<>(Arrays.asList(0, 1))).setDuration(Duration.NORMAL);
        Game expected = gameService.newGame(ng);
        
        Game actual = gameService.findById(expected.getId());
        Assertions.assertSame(expected, actual);
    }
    
    @Test
    void findAll() throws NotFoundException {
        
        ProfileEntity profile = new ProfileEntity().setName("name").setId(1);
        ProfileEntity profile1 = new ProfileEntity().setName("name1").setId(2);
        Mockito.when(profileService.find( Mockito.any(ProfileEntity.class) )).thenAnswer(invocation -> {
            ProfileEntity p = invocation.getArgument(0);
            return ( p.getId().equals(1) ) ? profile : profile1;
        });
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class))).thenReturn(10);
        
        NewGame ng = new NewGame().setProfilesId(new HashSet<>(Arrays.asList(0, 1))).setDuration(Duration.NORMAL);
        gameService.newGame(ng);
        gameService.newGame(ng);
        gameService.newGame(ng);
        
        Page<Game> actual = gameService.findAll(0, 2, false);
        Assertions.assertEquals( 2, actual.getTotalPages() );
        Assertions.assertEquals( 0, actual.getNumber() );
        Assertions.assertEquals( 3, actual.getTotalElements() );
        
        actual = gameService.findAll(3, 2, false);
        Assertions.assertEquals( 2, actual.getTotalPages() );
        Assertions.assertEquals( 3, actual.getNumber() );
        Assertions.assertEquals( 3, actual.getTotalElements() );
        
        actual = gameService.findAll(0, 2, true);
        Assertions.assertEquals( 0, actual.getTotalPages() );
        Assertions.assertEquals( 0, actual.getNumber() );
        Assertions.assertEquals( 0, actual.getTotalElements() );
    }
    
}
