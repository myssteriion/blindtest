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

public class ThiefTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        Thief thief = new Thief(-1,  -2, 3);
        Assert.assertEquals( 0, thief.getNbMusics() );
        Assert.assertEquals( 0, thief.getNbPointWon() );
        Assert.assertEquals( 0, thief.getNbPointLoose() );
    }
    
    @Test
    public void getterSetter() {
        
        Thief thief = new Thief(-1,  -2, 3);
        Assert.assertEquals( 0, thief.getNbMusics() );
        Assert.assertEquals( 0, thief.getNbPointWon() );
        Assert.assertEquals( 0, thief.getNbPointLoose() );
        
        thief = new Thief(5,  100, -100);
        Assert.assertEquals( 5, thief.getNbMusics() );
        Assert.assertEquals( 100, thief.getNbPointWon() );
        Assert.assertEquals( -100, thief.getNbPointLoose() );
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
        
        for (int i = 0; i < 52; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.THIEF, game.getRound() );
        Thief thief = (Thief) game.getRoundContent();
        
        try {
            thief.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            thief.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        
        Game actual = thief.apply(game, musicResult);
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );
        
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = thief.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 0, actual.getPlayers().get(0).getScore() );
        
        playersNames = Arrays.asList("name", "name", "name");
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = thief.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( -300, actual.getPlayers().get(0).getScore() );
        
        playersNames = Arrays.asList("name", "name", "name");
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = thief.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( -500, actual.getPlayers().get(0).getScore() );
    }
    
    @Test
    public void isFinished() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        for (int i = 0; i < 52; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.THIEF, game.getRound() );
        Thief thief = (Thief) game.getRoundContent();
        
        try {
            thief.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Assert.assertFalse( thief.isFinished(game) );
    }
    
    @Test
    public void isLast() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        for (int i = 0; i < 52; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.THIEF, game.getRound() );
        Thief thief = (Thief) game.getRoundContent();
        
        Assert.assertFalse( thief.isLastMusic(game) );
        
        for (int i = 0; i < 18; i++) {
            game.nextStep(roundProperties);
            Assert.assertFalse( thief.isLastMusic(game) );
        }
        
        game.nextStep(roundProperties);
        Assert.assertTrue( thief.isLastMusic(game) );
        
        game.nextStep(roundProperties);
        Assert.assertFalse( thief.isLastMusic(game) );
    }
    
    @Test
    public void toStringAndEquals() {
        
        Thief thief = new Thief(5,  100, -100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointLoose=-100", thief.toString() );
    }
    
}