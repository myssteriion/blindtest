package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LuckyContentTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        LuckyContent luckyContent = new LuckyContent(-1,  -2, -1);
        Assert.assertEquals( 0, luckyContent.getNbMusics() );
        Assert.assertEquals( 0, luckyContent.getNbPointWon() );
        Assert.assertEquals( 0, luckyContent.getNbPointBonus() );
        Assert.assertEquals( 0, luckyContent.getNbPlayers() );
    }
    
    @Test
    public void getterSetter() {
        
        LuckyContent luckyContent = new LuckyContent(-1,  -2, -1);
        Assert.assertEquals( 0, luckyContent.getNbMusics() );
        Assert.assertEquals( 0, luckyContent.getNbPointWon() );
        Assert.assertEquals( 0, luckyContent.getNbPointBonus() );
        Assert.assertEquals( 0, luckyContent.getNbPlayers() );
        
        luckyContent = new LuckyContent(5,  100, 100);
        Assert.assertEquals( 5, luckyContent.getNbMusics() );
        Assert.assertEquals( 100, luckyContent.getNbPointWon() );
        Assert.assertEquals( 100, luckyContent.getNbPointBonus() );
        Assert.assertEquals( 0, luckyContent.getNbPlayers() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name3")),
                new Player(new ProfileDTO().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        LuckyContent luckyContent = new LuckyContent(10, 100, 50);
        luckyContent.prepare(game);
        Assert.assertEquals( 1, luckyContent.getNbPlayers() );
    }
    
    @Test
    public void apply() {
        
        List<String> playersNames = Collections.singletonList("name");
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(musicDto).setAuthorWinners(playersNames);
        
        for (int i = 0; i < 32; i++)
            game.nextStep(roundContentProperties);
        
        Assert.assertSame( Round.LUCKY, game.getRound() );
        LuckyContent recoveryContent = (LuckyContent) game.getRoundContent();
        
        try {
            recoveryContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            recoveryContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        
        Game actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 150 && actual.getPlayers().get(0).getScore() <= 250);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(musicDto).setTitleWinners(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 300 && actual.getPlayers().get(0).getScore() <= 500);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(musicDto).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 600 && actual.getPlayers().get(0).getScore() <= 1000);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(musicDto).setLosers(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 600 && actual.getPlayers().get(0).getScore() <= 1000);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(musicDto).setPenalties(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 300 && actual.getPlayers().get(0).getScore() <= 700);
    }
    
    @Test
    public void toStringAndEquals() {
        
        LuckyContent luckyContent = new LuckyContent(5,  150, 100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=150, nbPointBonus=100, nbPlayers=0", luckyContent.toString() );
    }
    
}