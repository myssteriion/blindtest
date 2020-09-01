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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        Choice choice = new Choice(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choice.getNbMusics() );
        Assert.assertEquals( 0, choice.getNbPointWon() );
        Assert.assertEquals( 0, choice.getNbPointBonus() );
        Assert.assertEquals( 0, choice.getNbPointMalus() );
        Assert.assertEquals( new ArrayList<>(), choice.getOrder() );
    }
    
    @Test
    public void getterSetter() {
        
        Choice choice = new Choice(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choice.getNbMusics() );
        Assert.assertEquals( 0, choice.getNbPointWon() );
        Assert.assertEquals( 0, choice.getNbPointBonus() );
        Assert.assertEquals( 0, choice.getNbPointMalus() );
        Assert.assertEquals( new ArrayList<>(), choice.getOrder() );
        
        choice = new Choice(5,  100, 50, -50);
        Assert.assertEquals( 5, choice.getNbMusics() );
        Assert.assertEquals( 100, choice.getNbPointWon() );
        Assert.assertEquals( 50, choice.getNbPointBonus() );
        Assert.assertEquals( -50, choice.getNbPointMalus() );
        Assert.assertEquals( new ArrayList<>(), choice.getOrder() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Choice choice = new Choice(10, 100, 50, 50);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
        
        choice.prepare(game);
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() ^ game.getPlayers().get(1).isTurnToChoose() ^ game.getPlayers().get(2).isTurnToChoose() );
    }
    
    @Test
    public void apply() {
        
        List<String> playersNames = Arrays.asList("name", "name3", "name2");
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Integer gameId = 1;
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        
        for (int i = 0; i < 20; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.CHOICE, game.getRound() );
        Choice choice = (Choice) game.getRoundContent();
        
        game.getPlayers().get(0).setTurnToChoose(true);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
        
        
        try {
            choice.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            choice.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        Game actual = choice.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(true);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = choice.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 100-50, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(true);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        actual = choice.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 150+100, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 50+100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100+100+50, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(true);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = choice.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 250+150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 150+100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 250+100, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(true);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = choice.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 400+200, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 250+300, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 350+200, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(true);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = choice.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertEquals( 600-200, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 550-200, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 550-200-50, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(true);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(false);
    }
    
    @Test
    public void toStringAndEquals() {
        
        Choice choice = new Choice(5,  100, 50, -50);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointBonus=50, nbPointMalus=-50, order=[]", choice.toString() );
    }
    
}