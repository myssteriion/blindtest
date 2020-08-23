package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceContentTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        ChoiceContent choiceContent = new ChoiceContent(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choiceContent.getNbMusics() );
        Assert.assertEquals( 0, choiceContent.getNbPointWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointBonus() );
        Assert.assertEquals( 0, choiceContent.getNbPointMalus() );
        Assert.assertEquals( new ArrayList<>(), choiceContent.getOrder() );
    }
    
    @Test
    public void getterSetter() {
        
        ChoiceContent choiceContent = new ChoiceContent(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choiceContent.getNbMusics() );
        Assert.assertEquals( 0, choiceContent.getNbPointWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointBonus() );
        Assert.assertEquals( 0, choiceContent.getNbPointMalus() );
        Assert.assertEquals( new ArrayList<>(), choiceContent.getOrder() );
        
        choiceContent = new ChoiceContent(5,  100, 50, -50);
        Assert.assertEquals( 5, choiceContent.getNbMusics() );
        Assert.assertEquals( 100, choiceContent.getNbPointWon() );
        Assert.assertEquals( 50, choiceContent.getNbPointBonus() );
        Assert.assertEquals( -50, choiceContent.getNbPointMalus() );
        Assert.assertEquals( new ArrayList<>(), choiceContent.getOrder() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundContentProperties);
        
        ChoiceContent choiceContent = new ChoiceContent(10, 100, 50, 50);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
        
        choiceContent.prepare(game);
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() ^ game.getPlayers().get(1).isTurnToChoose() ^ game.getPlayers().get(2).isTurnToChoose() );
    }
    
    @Test
    public void apply() {
        
        List<String> playersNames = Arrays.asList("name", "name3", "name2");
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundContentProperties);
        
        Integer gameId = 1;
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        
        for (int i = 0; i < 20; i++)
            game.nextStep(roundContentProperties);
        
        Assert.assertSame( Round.CHOICE, game.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) game.getRoundContent();
        
        game.getPlayers().get(0).setTurnToChoose(true);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
        
        
        try {
            choiceContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            choiceContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        Game actual = choiceContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(true);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 100-50, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(true);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 150+100, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 50+100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100+100+50, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(true);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 250+150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 150+100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 250+100, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(true);
        game.getPlayers().get(2).setTurnToChoose(false);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 400+200, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 250+300, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 350+200, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(false);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(true);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep(roundContentProperties);
        Assert.assertEquals( 600-200, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 550-200, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 550-200-50, actual.getPlayers().get(2).getScore() );
        game.getPlayers().get(0).setTurnToChoose(true);
        game.getPlayers().get(1).setTurnToChoose(false);
        game.getPlayers().get(2).setTurnToChoose(false);
    }
    
    @Test
    public void toStringAndEquals() {
        
        ChoiceContent choiceContent = new ChoiceContent(5,  100, 50, -50);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointBonus=50, nbPointMalus=-50, order=[]", choiceContent.toString() );
    }
    
}