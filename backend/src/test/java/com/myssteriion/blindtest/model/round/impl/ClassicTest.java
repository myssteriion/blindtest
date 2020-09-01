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

public class ClassicTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        Classic classic = new Classic(-1,  -2);
        Assert.assertEquals( 0, classic.getNbMusics() );
        Assert.assertEquals( 0, classic.getNbPointWon() );
    }
    
    @Test
    public void getterSetter() {
        
        Classic classic = new Classic(-1,  -2);
        Assert.assertEquals( 0, classic.getNbMusics() );
        Assert.assertEquals( 0, classic.getNbPointWon() );
        
        classic = new Classic(5,  100);
        Assert.assertEquals( 5, classic.getNbMusics() );
        Assert.assertEquals( 100, classic.getNbPointWon() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Classic classic = new Classic(10, 100);
        classic.prepare(game);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
        
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
        
        Assert.assertSame( Round.CLASSIC, game.getRound() );
        Classic classic = (Classic) game.getRoundContent();
        
        try {
            classic.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            classic.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        
        Game actual = classic.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = classic.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 200, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = classic.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 400, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = classic.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 400, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = classic.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 200, actual.getPlayers().get(0).getScore() );
    }
    
    @Test
    public void isFinished() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Assert.assertSame( Round.CLASSIC, game.getRound() );
        Classic classic = (Classic) game.getRoundContent();
        
        try {
            classic.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Assert.assertFalse( classic.isFinished(game) );
        
        for (int i = 0; i < 19; i++) {
            game.nextStep(roundProperties);
            Assert.assertFalse( classic.isFinished(game) );
        }
        
        // le isFinish ne peut etre testé à "true" car le nextStep remet à 0 le "nbMusicsPlayedInRound"
        game.nextStep(roundProperties);
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
    }
    
    @Test
    public void isLast() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Assert.assertSame( Round.CLASSIC, game.getRound() );
        Classic classic = (Classic) game.getRoundContent();
        
        Assert.assertFalse( classic.isLastMusic(game) );
        
        for (int i = 0; i < 18; i++) {
            game.nextStep(roundProperties);
            Assert.assertFalse( classic.isLastMusic(game) );
        }
        
        game.nextStep(roundProperties);
        Assert.assertTrue( classic.isLastMusic(game) );
        
        game.nextStep(roundProperties);
        Assert.assertFalse( classic.isLastMusic(game) );
    }
    
    @Test
    public void toStringAndEquals() {
        
        Classic classic = new Classic(5,  100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100", classic.toString() );
    }
    
}