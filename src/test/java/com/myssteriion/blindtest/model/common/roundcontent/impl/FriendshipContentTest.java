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
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FriendshipContentTest extends AbstractTest {

    @Test
    public void constructors() {

        FriendshipContent friendshipContent = new FriendshipContent(-1,  -2);
        Assert.assertEquals( 0, friendshipContent.getNbMusics() );
        Assert.assertEquals( 0, friendshipContent.getNbPointWon() );
        Assert.assertEquals( 0, friendshipContent.getNbTeams() );
    }

    @Test
    public void getterSetter() {

        FriendshipContent friendshipContent = new FriendshipContent(-1,  -2);
        Assert.assertEquals( 0, friendshipContent.getNbMusics() );
        Assert.assertEquals( 0, friendshipContent.getNbPointWon() );
        Assert.assertEquals( 0, friendshipContent.getNbTeams() );

        friendshipContent = new FriendshipContent(5,  100);
        Assert.assertEquals( 5, friendshipContent.getNbMusics() );
        Assert.assertEquals( 100, friendshipContent.getNbPointWon() );
        Assert.assertEquals( 0, friendshipContent.getNbTeams() );
    }

    @Test
    public void prepareRound() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name3")),
                new Player(new ProfileDTO("name2")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, null, ConnectionMode.OFFLINE);

        FriendshipContent friendshipContent = new FriendshipContent(10, 100);
        Assert.assertEquals( -1, game.getPlayers().get(0).getTeamNumber() );
        Assert.assertEquals( -1, game.getPlayers().get(1).getTeamNumber() );
        Assert.assertEquals( -1, game.getPlayers().get(2).getTeamNumber() );

        friendshipContent.prepare(game);
        Assert.assertTrue( game.getPlayers().get(0).getTeamNumber() == 0 || game.getPlayers().get(0).getTeamNumber() == 1 );
        Assert.assertTrue( game.getPlayers().get(1).getTeamNumber() == 0 || game.getPlayers().get(1).getTeamNumber() == 1 );
        Assert.assertTrue( game.getPlayers().get(2).getTeamNumber() == 0 || game.getPlayers().get(2).getTeamNumber() == 1 );
    }

    @Test
    public void apply() {

        List<String> playersNames = Arrays.asList("name", "name3", "name2");
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name3")),
                new Player(new ProfileDTO("name2")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, null, ConnectionMode.OFFLINE);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        MusicResult musicResult = new MusicResult(gameId, musicDto, playersNames, null, null);

        for (int i = 0; i < 42; i++)
            game.nextStep();

        Assert.assertSame( Round.FRIENDSHIP, game.getRound() );
        FriendshipContent friendshipContent = (FriendshipContent) game.getRoundContent();

        Assert.assertTrue( game.getPlayers().get(0).getTeamNumber() != -1 );
        Assert.assertTrue( game.getPlayers().get(1).getTeamNumber() != -1 );
        Assert.assertTrue( game.getPlayers().get(2).getTeamNumber() != -1 );



        try {
            friendshipContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }

        try {
            friendshipContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }

        Game actual = friendshipContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 150, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 150, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, null, playersNames);
        actual = friendshipContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 150, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 150, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, playersNames, null, null);
        actual = friendshipContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 150+150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 150+150, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 150+150, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, playersNames, null);
        actual = friendshipContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 300+150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 300+150, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 300+150, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, playersNames, playersNames, null);
        actual = friendshipContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 450+300, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 450+300, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 450+300, actual.getPlayers().get(2).getScore() );

        for (int i = 6; i < 10; i++) {

            friendshipContent.apply(game, musicResult);
            game.nextStep();

            Assert.assertTrue( game.getPlayers().get(0).getTeamNumber() != -1 );
            Assert.assertTrue( game.getPlayers().get(1).getTeamNumber() != -1 );
            Assert.assertTrue( game.getPlayers().get(2).getTeamNumber() != -1 );
        }
    }

    @Test
    public void toStringAndEquals() {

        FriendshipContent friendshipContent = new FriendshipContent(5,  100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbTeams=0", friendshipContent.toString() );
    }

}