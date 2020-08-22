package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
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

public class ClassicContentTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        ClassicContent classicContent = new ClassicContent(-1,  -2);
        Assert.assertEquals( 0, classicContent.getNbMusics() );
        Assert.assertEquals( 0, classicContent.getNbPointWon() );
    }
    
    @Test
    public void getterSetter() {
        
        ClassicContent classicContent = new ClassicContent(-1,  -2);
        Assert.assertEquals( 0, classicContent.getNbMusics() );
        Assert.assertEquals( 0, classicContent.getNbPointWon() );
        
        classicContent = new ClassicContent(5,  100);
        Assert.assertEquals( 5, classicContent.getNbMusics() );
        Assert.assertEquals( 100, classicContent.getNbPointWon() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        ClassicContent classicContent = new ClassicContent(10, 100);
        classicContent.prepare(game);
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
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        Integer gameId = 1;
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        
        Assert.assertSame( Round.CLASSIC, game.getRound() );
        ClassicContent classicContent = (ClassicContent) game.getRoundContent();
        
        try {
            classicContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            classicContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        
        Game actual = classicContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = classicContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 200, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = classicContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 400, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = classicContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 400, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = classicContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 200, actual.getPlayers().get(0).getScore() );
    }
    
    @Test
    public void isFinished() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        Assert.assertSame( Round.CLASSIC, game.getRound() );
        ClassicContent classicContent = (ClassicContent) game.getRoundContent();
        
        try {
            classicContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Assert.assertFalse( classicContent.isFinished(game) );
        
        for (int i = 0; i < 19; i++) {
            game.nextStep(roundContentProperties);
            Assert.assertFalse( classicContent.isFinished(game) );
        }
        
        // le isFinish ne peut etre testé à "true" car le nextStep remet à 0 le "nbMusicsPlayedInRound"
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
    }
    
    @Test
    public void isLast() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        Assert.assertSame( Round.CLASSIC, game.getRound() );
        ClassicContent classicContent = (ClassicContent) game.getRoundContent();
        
        Assert.assertFalse( classicContent.isLastMusic(game) );
        
        for (int i = 0; i < 18; i++) {
            game.nextStep(roundContentProperties);
            Assert.assertFalse( classicContent.isLastMusic(game) );
        }
        
        game.nextStep(roundContentProperties);
        Assert.assertTrue( classicContent.isLastMusic(game) );
        
        game.nextStep(roundContentProperties);
        Assert.assertFalse( classicContent.isLastMusic(game) );
    }
    
    @Test
    public void toStringAndEquals() {
        
        ClassicContent classicContent = new ClassicContent(5,  100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100", classicContent.toString() );
    }
    
}