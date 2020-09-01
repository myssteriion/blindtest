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

public class RecoveryContentTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        RecoveryContent recoveryContent = new RecoveryContent(-1,  -2);
        Assert.assertEquals( 0, recoveryContent.getNbMusics() );
        Assert.assertEquals( 0, recoveryContent.getNbPointWon() );
    }
    
    @Test
    public void getterSetter() {
        
        RecoveryContent recoveryContent = new RecoveryContent(-1,  -2);
        Assert.assertEquals( 0, recoveryContent.getNbMusics() );
        Assert.assertEquals( 0, recoveryContent.getNbPointWon() );
        
        recoveryContent = new RecoveryContent(5,  100);
        Assert.assertEquals( 5, recoveryContent.getNbMusics() );
        Assert.assertEquals( 100, recoveryContent.getNbPointWon() );
    }
    
    @Test
    public void apply() {
        
        List<String> playersNames = Collections.singletonList("name");
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name1")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundContentProperties);
        
        Integer gameId = 1;
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        
        for (int i = 0; i < 72; i++)
            game.nextStep(roundContentProperties);
        
        Assert.assertSame( Round.RECOVERY, game.getRound() );
        RecoveryContent recoveryContent = (RecoveryContent) game.getRoundContent();
        
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
        Assert.assertEquals( 30, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 60, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 120, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 120, actual.getPlayers().get(0).getScore() );
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 60, actual.getPlayers().get(0).getScore() );
    }
    
}