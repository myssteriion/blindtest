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
    public void apply() {

        List<String> playersNames = Collections.singletonList("name");
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult(gameId, musicDto, playersNames, null, null);

        Assert.assertSame( Round.CLASSIC, game.getRound() );
        ClassicContent classicContent = (ClassicContent) game.getRoundContent();

        try {
            classicContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        try {
            classicContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResultDto' est obligatoire."), e);
        }


        Game actual = classicContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, playersNames, null);
        actual = classicContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 200, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, playersNames, playersNames, null);
        actual = classicContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 400, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, null, playersNames);
        actual = classicContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 400, actual.getPlayers().get(0).getScore() );
    }

    @Test
    public void isFinished() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL);

        Assert.assertSame( Round.CLASSIC, game.getRound() );
        ClassicContent classicContent = (ClassicContent) game.getRoundContent();

        try {
            classicContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        Assert.assertFalse( classicContent.isFinished(game) );

        for (int i = 0; i < 19; i++) {
            game.nextStep();
            Assert.assertFalse( classicContent.isFinished(game) );
        }

        // le isFinish ne peut etre testé à "true" car le nextStep remet à 0 le "nbMusicsPlayedInRound"
        game.nextStep();
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
    }

    @Test
    public void isLast() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL);

        Assert.assertSame( Round.CLASSIC, game.getRound() );
        ClassicContent classicContent = (ClassicContent) game.getRoundContent();

        Assert.assertFalse( classicContent.isLastMusic(game) );

        for (int i = 0; i < 18; i++) {
            game.nextStep();
            Assert.assertFalse( classicContent.isLastMusic(game) );
        }

        game.nextStep();
        Assert.assertTrue( classicContent.isLastMusic(game) );

        game.nextStep();
        Assert.assertFalse( classicContent.isLastMusic(game) );
    }

    @Test
    public void toStringAndEquals() {

        ClassicContent classicContent = new ClassicContent(5,  100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100", classicContent.toString() );
    }

}