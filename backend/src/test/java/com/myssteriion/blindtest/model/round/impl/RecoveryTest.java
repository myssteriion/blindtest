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

public class RecoveryTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        Recovery recovery = new Recovery(-1,  -2);
        Assert.assertEquals( 0, recovery.getNbMusics() );
        Assert.assertEquals( 0, recovery.getNbPointWon() );
    }
    
    @Test
    public void getterSetter() {
        
        Recovery recovery = new Recovery(-1,  -2);
        Assert.assertEquals( 0, recovery.getNbMusics() );
        Assert.assertEquals( 0, recovery.getNbPointWon() );
        
        recovery = new Recovery(5,  100);
        Assert.assertEquals( 5, recovery.getNbMusics() );
        Assert.assertEquals( 100, recovery.getNbPointWon() );
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
        
        for (int i = 0; i < 72; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.RECOVERY, game.getRound() );
        Recovery recovery = (Recovery) game.getRoundContent();
        
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
        Assert.assertEquals( 30, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 60, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 120, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 120, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = recovery.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 60, actual.getPlayers().get(0).getScore() );
    }
    
}