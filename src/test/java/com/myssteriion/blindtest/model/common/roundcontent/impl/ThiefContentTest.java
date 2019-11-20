package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ThiefContentTest extends AbstractTest {

    @Test
    public void constructors() {

        ThiefContent thiefContent = new ThiefContent(-1,  -2, 3);
        Assert.assertEquals( 0, thiefContent.getNbMusics() );
        Assert.assertEquals( 0, thiefContent.getNbPointWon() );
        Assert.assertEquals( 0, thiefContent.getNbPointLoose() );
    }

    @Test
    public void getterSetter() {

        ThiefContent thiefContent = new ThiefContent(-1,  -2, 3);
        Assert.assertEquals( 0, thiefContent.getNbMusics() );
        Assert.assertEquals( 0, thiefContent.getNbPointWon() );
        Assert.assertEquals( 0, thiefContent.getNbPointLoose() );

        thiefContent = new ThiefContent(5,  100, -100);
        Assert.assertEquals( 5, thiefContent.getNbMusics() );
        Assert.assertEquals( 100, thiefContent.getNbPointWon() );
        Assert.assertEquals( -100, thiefContent.getNbPointLoose() );
    }

    @Test
    public void apply() {

        List<String> playersNames = Collections.singletonList("name");
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult(gameId, musicDto, playersNames, null, null);

        for (int i = 0; i < 28; i++)
            game.nextStep();

        Assert.assertSame( Round.THIEF, game.getRound() );
        ThiefContent thiefContent = (ThiefContent) game.getRoundContent();

        try {
            thiefContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }

        try {
            thiefContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }


        Game actual = thiefContent.apply(game, musicResult);
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );


        musicResult = new MusicResult(gameId, musicDto, null, null, playersNames);
        actual = thiefContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 0, actual.getPlayers().get(0).getScore() );

        playersNames = Arrays.asList("name", "name", "name");
        musicResult = new MusicResult(gameId, musicDto, null, null, playersNames);
        actual = thiefContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( -300, actual.getPlayers().get(0).getScore() );
    }

    @Test
    public void isFinished() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null);

        for (int i = 0; i < 28; i++)
            game.nextStep();

        Assert.assertSame( Round.THIEF, game.getRound() );
        ThiefContent thiefContent = (ThiefContent) game.getRoundContent();

        try {
            thiefContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }

        Assert.assertFalse( thiefContent.isFinished(game) );
    }

    @Test
    public void isLast() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null);

        for (int i = 0; i < 28; i++)
            game.nextStep();

        Assert.assertSame( Round.THIEF, game.getRound() );
        ThiefContent thiefContent = (ThiefContent) game.getRoundContent();

        Assert.assertFalse( thiefContent.isLastMusic(game) );

        for (int i = 0; i < 18; i++) {
            game.nextStep();
            Assert.assertFalse( thiefContent.isLastMusic(game) );
        }

        game.nextStep();
        Assert.assertTrue( thiefContent.isLastMusic(game) );

        game.nextStep();
        Assert.assertFalse( thiefContent.isLastMusic(game) );
    }

    @Test
    public void toStringAndEquals() {

        ThiefContent thiefContent = new ThiefContent(5,  100, -100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointLoose=-100", thiefContent.toString() );
    }

}