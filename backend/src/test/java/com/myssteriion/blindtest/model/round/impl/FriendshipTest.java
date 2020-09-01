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
import java.util.List;

public class FriendshipTest extends AbstractTest {
    
    @Test
    public void constructors() {
        
        Friendship friendship = new Friendship(-1,  -2);
        Assert.assertEquals( 0, friendship.getNbMusics() );
        Assert.assertEquals( 0, friendship.getNbPointWon() );
        Assert.assertEquals( 0, friendship.getNbTeams() );
    }
    
    @Test
    public void getterSetter() {
        
        Friendship friendship = new Friendship(-1,  -2);
        Assert.assertEquals( 0, friendship.getNbMusics() );
        Assert.assertEquals( 0, friendship.getNbPointWon() );
        Assert.assertEquals( 0, friendship.getNbTeams() );
        
        friendship = new Friendship(5,  100);
        Assert.assertEquals( 5, friendship.getNbMusics() );
        Assert.assertEquals( 100, friendship.getNbPointWon() );
        Assert.assertEquals( 0, friendship.getNbTeams() );
    }
    
    @Test
    public void prepareRound() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name3")),
                new Player(new ProfileEntity().setName("name2")));
        Game game = new Game(players, Duration.NORMAL, null, null, roundProperties);
        
        Friendship friendship = new Friendship(10, 100);
        Assert.assertEquals( -1, game.getPlayers().get(0).getTeamNumber() );
        Assert.assertEquals( -1, game.getPlayers().get(1).getTeamNumber() );
        Assert.assertEquals( -1, game.getPlayers().get(2).getTeamNumber() );
        
        friendship.prepare(game);
        Assert.assertTrue( game.getPlayers().get(0).getTeamNumber() == 0 || game.getPlayers().get(0).getTeamNumber() == 1 );
        Assert.assertTrue( game.getPlayers().get(1).getTeamNumber() == 0 || game.getPlayers().get(1).getTeamNumber() == 1 );
        Assert.assertTrue( game.getPlayers().get(2).getTeamNumber() == 0 || game.getPlayers().get(2).getTeamNumber() == 1 );
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
        
        for (int i = 0; i < 42; i++)
            game.nextStep(roundProperties);
        
        Assert.assertSame( Round.FRIENDSHIP, game.getRound() );
        Friendship friendship = (Friendship) game.getRoundContent();
        
        Assert.assertTrue( game.getPlayers().get(0).getTeamNumber() != -1 );
        Assert.assertTrue( game.getPlayers().get(1).getTeamNumber() != -1 );
        Assert.assertTrue( game.getPlayers().get(2).getTeamNumber() != -1 );
        
        
        
        try {
            friendship.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        try {
            friendship.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        Game actual = friendship.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 150 && actual.getPlayers().get(0).getScore() <= 300);
        Assert.assertTrue( actual.getPlayers().get(1).getScore() >= 150 && actual.getPlayers().get(1).getScore() <= 300);
        Assert.assertTrue( actual.getPlayers().get(2).getScore() >= 150 && actual.getPlayers().get(2).getScore() <= 300);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setLosers(playersNames);
        actual = friendship.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 150 && actual.getPlayers().get(0).getScore() <= 300);
        Assert.assertTrue( actual.getPlayers().get(1).getScore() >= 150 && actual.getPlayers().get(1).getScore() <= 300);
        Assert.assertTrue( actual.getPlayers().get(2).getScore() >= 150 && actual.getPlayers().get(2).getScore() <= 300);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames);
        actual = friendship.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 300 && actual.getPlayers().get(0).getScore() <= 600);
        Assert.assertTrue( actual.getPlayers().get(1).getScore() >= 300 && actual.getPlayers().get(1).getScore() <= 600);
        Assert.assertTrue( actual.getPlayers().get(2).getScore() >= 300 && actual.getPlayers().get(2).getScore() <= 600);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setTitleWinners(playersNames);
        actual = friendship.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 450 && actual.getPlayers().get(0).getScore() <= 900);
        Assert.assertTrue( actual.getPlayers().get(1).getScore() >= 450 && actual.getPlayers().get(1).getScore() <= 900);
        Assert.assertTrue( actual.getPlayers().get(2).getScore() >= 450 && actual.getPlayers().get(2).getScore() <= 900);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setAuthorWinners(playersNames).setTitleWinners(playersNames);
        actual = friendship.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 750 && actual.getPlayers().get(0).getScore() <= 1500);
        Assert.assertTrue( actual.getPlayers().get(1).getScore() >= 750 && actual.getPlayers().get(1).getScore() <= 1500);
        Assert.assertTrue( actual.getPlayers().get(2).getScore() >= 750 && actual.getPlayers().get(2).getScore() <= 1500);
        
        musicResult = new MusicResult().setGameId(gameId).setMusic(music).setPenalties(playersNames);
        actual = friendship.apply(game, musicResult);
        game.nextStep(roundProperties);
        Assert.assertTrue( actual.getPlayers().get(0).getScore() >= 450 && actual.getPlayers().get(0).getScore() <= 1200);
        Assert.assertTrue( actual.getPlayers().get(1).getScore() >= 450 && actual.getPlayers().get(1).getScore() <= 1200);
        Assert.assertTrue( actual.getPlayers().get(2).getScore() >= 450 && actual.getPlayers().get(2).getScore() <= 1200);
        
        for (int i = 7; i < 10; i++) {
            
            friendship.apply(game, musicResult);
            game.nextStep(roundProperties);
            
            Assert.assertTrue( game.getPlayers().get(0).getTeamNumber() != -1 );
            Assert.assertTrue( game.getPlayers().get(1).getTeamNumber() != -1 );
            Assert.assertTrue( game.getPlayers().get(2).getTeamNumber() != -1 );
        }
    }
    
    @Test
    public void toStringAndEquals() {
        
        Friendship friendship = new Friendship(5,  100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbTeams=0", friendship.toString() );
    }
    
}