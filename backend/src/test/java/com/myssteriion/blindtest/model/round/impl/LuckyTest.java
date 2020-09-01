package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.round.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LuckyTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        Lucky lucky = new Lucky(-1,  -2, -1);
        Assert.assertEquals( 0, lucky.getNbMusics() );
        Assert.assertEquals( 0, lucky.getNbPointWon() );
        Assert.assertEquals( 0, lucky.getNbPointBonus() );
        Assert.assertEquals( 0, lucky.getNbPlayers() );
    }
    
    @Test
    public void getterSetter() {
        
        Lucky lucky = new Lucky(-1,  -2, -1);
        Assert.assertEquals( 0, lucky.getNbMusics() );
        Assert.assertEquals( 0, lucky.getNbPointWon() );
        Assert.assertEquals( 0, lucky.getNbPointBonus() );
        Assert.assertEquals( 0, lucky.getNbPlayers() );
        
        lucky = new Lucky(5,  100, 100);
        Assert.assertEquals( 5, lucky.getNbMusics() );
        Assert.assertEquals( 100, lucky.getNbPointWon() );
        Assert.assertEquals( 100, lucky.getNbPointBonus() );
        Assert.assertEquals( 0, lucky.getNbPlayers() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Lucky lucky = new Lucky(10, 100, 50);
        lucky.prepare(game);
        Assert.assertEquals( 1, lucky.getNbPlayers() );
    }
    
    @Test
    public void apply() {
        
        List<String> playersNames = Collections.singletonList("name");
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Integer gameId = 1;
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        
        for (int i = 0; i < 32; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.LUCKY, game.getRound() );
        Lucky recovery = (Lucky) game.getRoundContent();
        
        try {
            recovery.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            recovery.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        
        Game actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 150 && actual.getPlayers().get(0).getScore() <= 250);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 300 && actual.getPlayers().get(0).getScore() <= 500);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 600 && actual.getPlayers().get(0).getScore() <= 1000);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 600 && actual.getPlayers().get(0).getScore() <= 1000);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 300 && actual.getPlayers().get(0).getScore() <= 700);
    }
    
    @Test
    public void toStringAndEquals() {
        
        Lucky lucky = new Lucky(5,  150, 100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=150, nbPointBonus=100, nbPlayers=0", lucky.toString() );
    }
    
}