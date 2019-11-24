package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.ConnectionMode;
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

public class ChoiceContentTest extends AbstractTest {

    @Test
    public void constructors() {

        ChoiceContent choiceContent = new ChoiceContent(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choiceContent.getNbMusics() );
        Assert.assertEquals( 0, choiceContent.getNbPointWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointBonus() );
        Assert.assertEquals( 0, choiceContent.getNbPointMalus() );
    }

    @Test
    public void getterSetter() {

        ChoiceContent choiceContent = new ChoiceContent(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choiceContent.getNbMusics() );
        Assert.assertEquals( 0, choiceContent.getNbPointWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointBonus() );
        Assert.assertEquals( 0, choiceContent.getNbPointMalus() );

        choiceContent = new ChoiceContent(5,  100, 50, -50);
        Assert.assertEquals( 5, choiceContent.getNbMusics() );
        Assert.assertEquals( 100, choiceContent.getNbPointWon() );
        Assert.assertEquals( 50, choiceContent.getNbPointBonus() );
        Assert.assertEquals( -50, choiceContent.getNbPointMalus() );
    }

    @Test
    public void apply() {

        List<String> playersNames = Arrays.asList("name", "name3", "name2");
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name3")),
                new Player(new ProfileDTO("name2")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, ConnectionMode.OFFLINE);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult(gameId, musicDto, playersNames, null, null);

        for (int i = 0; i < 20; i++)
            game.nextStep();

        Assert.assertSame( Round.CHOICE, game.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) game.getRoundContent();

        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );


        try {
            choiceContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }

        try {
            choiceContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }

        Game actual = choiceContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, null, playersNames);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 100-50, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, playersNames, null, null);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 150+100, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 50+100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 100+100+50, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, playersNames, null);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 250+150, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 150+100, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 250+100, actual.getPlayers().get(2).getScore() );

        musicResult = new MusicResult(gameId, musicDto, playersNames, playersNames, null);
        actual = choiceContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 400+200, actual.getPlayers().get(0).getScore() );
        Assert.assertEquals( 250+300, actual.getPlayers().get(1).getScore() );
        Assert.assertEquals( 350+200, actual.getPlayers().get(2).getScore() );

        for (int i = 6; i < 12; i++) {

            choiceContent.apply(game, musicResult);
            game.nextStep();

            if (i%3 == 0) {
                Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() );
                Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
                Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
            }
            else if (i%3 == 1) {
                Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
                Assert.assertTrue( game.getPlayers().get(1).isTurnToChoose() );
                Assert.assertFalse( game.getPlayers().get(2).isTurnToChoose() );
            }
            else {
                Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
                Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
                Assert.assertTrue( game.getPlayers().get(2).isTurnToChoose() );
            }
        }
    }

    @Test
    public void isFinished() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, ConnectionMode.OFFLINE);

        for (int i = 0; i < 20; i++)
            game.nextStep();

        Assert.assertSame( Round.CHOICE, game.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) game.getRoundContent();

        try {
            choiceContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }

        Assert.assertFalse( choiceContent.isFinished(game) );
    }

    @Test
    public void isLast() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, ConnectionMode.OFFLINE);

        for (int i = 0; i < 20; i++)
            game.nextStep();

        Assert.assertSame( Round.CHOICE, game.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) game.getRoundContent();

        Assert.assertFalse( choiceContent.isLastMusic(game) );

        for (int i = 0; i < (4*players.size())-2; i++) {
            game.nextStep();
            Assert.assertFalse( choiceContent.isLastMusic(game) );
        }

        game.nextStep();
        Assert.assertTrue( choiceContent.isLastMusic(game) );

        game.nextStep();
        Assert.assertFalse( choiceContent.isLastMusic(game) );
    }

    @Test
    public void toStringAndEquals() {

        ChoiceContent choiceContent = new ChoiceContent(5,  100, 50, -50);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointBonus=50, nbPointMalus=-50", choiceContent.toString() );
    }

}