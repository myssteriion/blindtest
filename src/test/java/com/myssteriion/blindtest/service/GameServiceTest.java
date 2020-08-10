package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.NewGame;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

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
    
    @Mock
    private SpotifyService spotifyService;
    
    @InjectMocks
    private GameService gameService;
    
    
    
    @Test
    public void newGame() throws NotFoundException, SpotifyException {
        
        ProfileDTO profileDto = (ProfileDTO) new ProfileDTO("name", "avatarName").setId(0);
        ProfileDTO profileDto1 = (ProfileDTO) new ProfileDTO("name1", "avatarName").setId(1);
        Mockito.when(profileService.find( Mockito.any(ProfileDTO.class) )).thenReturn(null).thenAnswer(invocation -> {
            ProfileDTO p = invocation.getArgument(0);
            return ( p.getId().equals(0) ) ? profileDto : profileDto1;
        });
        Mockito.doThrow(new SpotifyException("se")).when(spotifyService).testConnection();
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(0, 10);
        
        List<Integer> profilesId = Arrays.asList(0, 1);
        
        try {
            gameService.newGame(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'newGame' est obligatoire."), e);
        }
        
        try {
            gameService.newGame( new NewGame(new HashSet<>(profilesId), Duration.NORMAL, false, Collections.singletonList(Theme.ANNEES_60), null, ConnectionMode.OFFLINE) );
            Assert.fail("Doit lever une NotFoundException car un mock (musicService) return 0.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("Zero music found ('ANNEES_60' ; '[OFFLINE]')"), e);
        }
        
        try {
            gameService.newGame( new NewGame(new HashSet<>(profilesId), Duration.NORMAL, false, null, null, ConnectionMode.OFFLINE) );
            Assert.fail("Doit lever une NotFoundException car un param est KO.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("Profile '0' not found."), e);
        }
        
        
        
        Game game = gameService.newGame( new NewGame(new HashSet<>(profilesId), Duration.NORMAL, false, null, null, ConnectionMode.OFFLINE) );
        Assert.assertEquals( profilesId.size(), game.getPlayers().size() );
        
        game = gameService.newGame( new NewGame(new HashSet<>(Arrays.asList(0, 1)), Duration.NORMAL, false, null, null, ConnectionMode.OFFLINE) );
        Assert.assertEquals( 2, game.getPlayers().size() );
        
        try {
            gameService.newGame( new NewGame(new HashSet<>(Arrays.asList(0, 1)), Duration.NORMAL, false, null, null, ConnectionMode.ONLINE) );
            Assert.fail("Doit lever une car la connection spotify est KO.");
        }
        catch (SpotifyException e) {
            TestUtils.verifyException( new SpotifyException("se"), e);
        }
    }
    
    @Test
    public void apply() throws NotFoundException, SpotifyException, ConflictException {
        
        MusicDTO music = new MusicDTO("name", Theme.ANNEES_60, ConnectionMode.OFFLINE);
        MusicDTO music2 = new MusicDTO("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        
        ProfileDTO profile = (ProfileDTO) new ProfileDTO("name","avatarName").setId(1);
        ProfileDTO profile1 = (ProfileDTO) new ProfileDTO("name1","avatarName").setId(2);
        ProfileDTO profile2 = (ProfileDTO) new ProfileDTO("name2","avatarName").setId(3);
        ProfileStatDTO profileStat;
        ProfileStatDTO profileStat1;
        ProfileStatDTO profileStat2;
        
        Mockito.doNothing().when(musicService).refresh();
        Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(null, music2, music);
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(10);
    
        ProfileDTO finalProfile = profile;
        ProfileDTO finalProfile1 = profile1;
        ProfileDTO finalProfile2 = profile2;
        Mockito.when(profileService.find( Mockito.any(ProfileDTO.class) )).thenAnswer(invocation -> {
            ProfileDTO p = invocation.getArgument(0);
            switch ( p.getId() ) {
                case 1: return finalProfile;
                case 2: return finalProfile1;
                default: return finalProfile2;
            }
        });
        
        List<Integer> profilesId = Arrays.asList(0, 1, 2);
        MusicResult musicResult = new MusicResult( 0, music,
                Collections.singletonList(profile.getName()), null,
                Collections.singletonList(profile.getName()), null);
        
        
        try {
            gameService.apply(null);
            Assert.fail("Doit lever une IllegalArgumentException le gameDto n'est pas retrouvée.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        try {
            gameService.apply(musicResult);
            Assert.fail("Doit lever une NotFoundException le gameDto n'est pas retrouvée.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("Game not found."), e);
        }
        
        
        gameService.newGame( new NewGame(new HashSet<>(profilesId), Duration.NORMAL, false, Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70), null, ConnectionMode.OFFLINE) );
        
        
        try {
            gameService.apply(musicResult);
            Assert.fail("Doit lever une NotFoundException car le mock return null.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("Music not found."), e);
        }
        
        try {
            
            musicResult = new MusicResult( 0, music2,
                    Collections.singletonList(profile.getName()), null,
                    Collections.singletonList(profile.getName()), null);
            
            gameService.apply(musicResult);
            Assert.fail("Doit lever une NotFoundException car le theme de la musique n'est pas dans la game.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("'ANNEES_80' not found for this game ([ANNEES_60, ANNEES_70])."), e);
        }
        
        music = new MusicDTO("name", Theme.ANNEES_60, ConnectionMode.OFFLINE);
        profile = (ProfileDTO) new ProfileDTO("name", "avatarName").setId(1);
        profile1 = (ProfileDTO) new ProfileDTO("name1","avatarName").setId(2);
        profile2 = (ProfileDTO) new ProfileDTO("name2","avatarName").setId(3);
        profileStat = new ProfileStatDTO(1);
        profileStat1 = new ProfileStatDTO(2);
        profileStat2 = new ProfileStatDTO(3);
        
        // refaire les when car les objets ont subit un new
        Mockito.when(musicService.find( Mockito.any(MusicDTO.class) )).thenReturn(music);
        Mockito.when(musicService.update( Mockito.any(MusicDTO.class) )).thenReturn(music);
        Mockito.when(profileStatService.findByProfile( new ProfileDTO("name") )).thenReturn(profileStat);
        Mockito.when(profileStatService.findByProfile( new ProfileDTO("name1") )).thenReturn(profileStat1);
        Mockito.when(profileStatService.findByProfile( new ProfileDTO("name2") )).thenReturn(profileStat2);
        Mockito.when(profileStatService.update( new ProfileStatDTO(1) )).thenReturn(profileStat);
        Mockito.when(profileStatService.update( new ProfileStatDTO(2) )).thenReturn(profileStat1);
        Mockito.when(profileStatService.update( new ProfileStatDTO(3) )).thenReturn(profileStat2);
        
        
        List<String> playersName = Collections.singletonList(profile.getName());
        musicResult = new MusicResult( 0, music, playersName, null, null, null );
        Game game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(1), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(1).getScore() );
        Assert.assertNull( game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 2, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 1, game.getNbMusicsPlayed() );
        Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(1), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 1, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(1), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(1), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( 0, profileStat1.getBestScores().size() );
        Assert.assertEquals( new Integer(1), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertNull( profileStat1.getFoundMusics().get(Theme.ANNEES_60) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        musicResult = new MusicResult( 0, music, null, null, playersName, null );
        game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 100, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(1), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(1).getScore() );
        Assert.assertNull( game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 2, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 2, game.getNbMusicsPlayed() );
        Assert.assertEquals( 2, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(2), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 2, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(2), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(1), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(2), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertNull( profileStat1.getFoundMusics().get(Theme.ANNEES_60) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        musicResult = new MusicResult( 0, music, playersName, null, null, null );
        game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 200, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(2), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(1).getScore() );
        Assert.assertNull( game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 2, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 3, game.getNbMusicsPlayed() );
        Assert.assertEquals( 3, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(3), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 3, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(3), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(3), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertNull( profileStat1.getFoundMusics().get(Theme.ANNEES_60) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        playersName = Collections.singletonList(profile1.getName());
        musicResult = new MusicResult( 0, music, playersName, null, null, null );
        game = gameService.apply(musicResult);
        game = gameService.apply(musicResult);
        game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 200, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(2), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 2, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 6, game.getNbMusicsPlayed() );
        Assert.assertEquals( 6, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(6), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 6, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(6), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(6), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        playersName = Collections.singletonList(profile.getName());
        musicResult = new MusicResult( 0, music, playersName, null, null, null );
        game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 300, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 7, game.getNbMusicsPlayed() );
        Assert.assertEquals( 7, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(7), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 7, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(7), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(7), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        playersName = Collections.singletonList(profile.getName());
        musicResult = new MusicResult( 0, music, null, playersName, null, null );
        game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 400, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 8, game.getNbMusicsPlayed() );
        Assert.assertEquals( 8, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(8), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 8, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(8), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(8), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        playersName = Collections.singletonList(profile.getName());
        musicResult = new MusicResult( 0, music, playersName, playersName, null, null );
        game = gameService.apply(musicResult);
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( 600, game.getPlayers().get(0).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(0).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 1, game.getPlayers().get(0).getRank() );
        Assert.assertEquals( 300, game.getPlayers().get(1).getScore() );
        Assert.assertEquals( new Integer(3), game.getPlayers().get(1).getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( 2, game.getPlayers().get(1).getRank() );
        Assert.assertEquals( 0, game.getPlayers().get(2).getScore() );
        Assert.assertEquals( 3, game.getPlayers().get(2).getRank() );
        Assert.assertEquals( 9, game.getNbMusicsPlayed() );
        Assert.assertEquals( 9, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( new Integer(9), game.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( 9, music.getPlayed() );
        Assert.assertEquals( 0, profileStat.getBestScores().size() );
        Assert.assertEquals( new Integer(9), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(9), profileStat1.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( new Integer(3), profileStat1.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );
        Assert.assertNull( profileStat1.getPlayedGames().get(game.getDuration()) );
        
        musicResult = new MusicResult( 0, music, playersName, null, null, null );
        while (game.getRound() != null)
            game = gameService.apply(musicResult);
        
        Assert.assertTrue( game.isFinished() );
        Assert.assertEquals( new Integer(1), profileStat.getPlayedGames().get(game.getDuration()) );
        Assert.assertEquals( new Integer(1), profileStat1.getPlayedGames().get(game.getDuration()) );
        
        // classic : 15 à 100pts / 1 à 200pts (15 car 20 - 1 loser - 3 ou profile1 gagne - 1 ou profile gagne auteur et titre)
        // choice : 4 à 150pts / 8 à 100 pts
        // lucky : 10 à 150pts / 10 à 100 pts (le 10*100 c'est la aléatoire)
        // friendship : 10 à 150pts
        // thief : 20 à 100pts
        // recovery : 10 à 30pts
        Assert.assertTrue( profileStat.getBestScores().get(Duration.NORMAL) >= new Integer(15*100 + 1*200 + 4*150 + 8*100 + 10*150 + 10*150 + 20*100 + 10*30)
                && profileStat.getBestScores().get(Duration.NORMAL) <= new Integer(15*100 + 1*200 + 4*150 + 8*100 + 10*150 + 10*100 + 10*150 + 20*100 + 10*30) );
    }
    
    @Test
    public void findById() throws NotFoundException, SpotifyException {
        
        try {
            gameService.findById(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'id' est obligatoire."), e);
        }
        
        
        try {
            gameService.findById(10);
            Assert.fail("Doit lever une NotFoundException car le dto n'existe pas.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("Game not found."), e);
        }
        
        
        ProfileDTO profileDto = (ProfileDTO) new ProfileDTO("name", "avatarName").setId(1);
        ProfileDTO profileDto1 = (ProfileDTO) new ProfileDTO("name1", "avatarName").setId(2);
        Mockito.when(profileService.find( Mockito.any(ProfileDTO.class) )).thenAnswer(invocation -> {
            ProfileDTO p = invocation.getArgument(0);
            return ( p.getId().equals(1) ) ? profileDto : profileDto1;
        });
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(10);
        
        NewGame ng = new NewGame(new HashSet<>(Arrays.asList(0, 1)), Duration.NORMAL, false, null, null, ConnectionMode.OFFLINE);
        Game expected = gameService.newGame(ng);
        
        Game actual = gameService.findById(expected.getId());
        Assert.assertSame(expected, actual);
    }
    
    @Test
    public void findAll() throws NotFoundException, SpotifyException {
        
        ProfileDTO profileDto = (ProfileDTO) new ProfileDTO("name", "avatarName").setId(1);
        ProfileDTO profileDto1 = (ProfileDTO) new ProfileDTO("name1", "avatarName").setId(2);
        Mockito.when(profileService.find( Mockito.any(ProfileDTO.class) )).thenAnswer(invocation -> {
            ProfileDTO p = invocation.getArgument(0);
            return ( p.getId().equals(1) ) ? profileDto : profileDto1;
        });
        Mockito.when(musicService.getMusicNumber(Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(10);
        
        NewGame ng = new NewGame(new HashSet<>(Arrays.asList(0, 1)), Duration.NORMAL, false, null, null, ConnectionMode.OFFLINE);
        Game expected = gameService.newGame(ng);
        gameService.newGame(ng);
        gameService.newGame(ng);
        
        Page<Game> actual = gameService.findAll(0, 2, false);
        Assert.assertEquals( 2, actual.getTotalPages() );
        Assert.assertEquals( 0, actual.getNumber() );
        Assert.assertEquals( 3, actual.getTotalElements() );
    
        actual = gameService.findAll(3, 2, false);
        Assert.assertEquals( 2, actual.getTotalPages() );
        Assert.assertEquals( 3, actual.getNumber() );
        Assert.assertEquals( 3, actual.getTotalElements() );
        
        actual = gameService.findAll(0, 2, true);
        Assert.assertEquals( 0, actual.getTotalPages() );
        Assert.assertEquals( 0, actual.getNumber() );
        Assert.assertEquals( 0, actual.getTotalElements() );
    }
    
}
