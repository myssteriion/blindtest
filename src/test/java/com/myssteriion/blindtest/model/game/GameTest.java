package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameTest extends AbstractTest {

    @Test
    public void constructor() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));

        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);


        try {
            new Game(null, duration, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'players' est obligatoire."), e);
        }

        try {
            new Game(new HashSet<>(players), null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        try {
            new Game(new HashSet<>(), duration, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'players' est obligatoire."), e);
        }

        try {
            new Game(new HashSet<>(Collections.singletonList(new Player(new ProfileDTO("name")))), duration, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("2 players at minimum"), e);
        }

        Game game = new Game(new HashSet<>(players), duration, null);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );


        players = Arrays.asList(
                new Player(new ProfileDTO("name4")),
                new Player(new ProfileDTO("name1")),
                new Player(new ProfileDTO("name3")),
                new Player(new ProfileDTO("name2")));

        game = new Game(new HashSet<>(players), duration, themes);
        Assert.assertEquals( players.get(1), game.getPlayers().get(0) );
        Assert.assertEquals( players.get(3), game.getPlayers().get(1) );
        Assert.assertEquals( players.get(2), game.getPlayers().get(2) );
        Assert.assertEquals( players.get(0), game.getPlayers().get(3) );
    }

    @Test
    public void getterSetter() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);

        Game game = new Game(new HashSet<>(players), duration, null);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( Theme.getSortedTheme(), game.getThemes() );

        game = new Game(new HashSet<>(players), duration, themes);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( themes, game.getThemes() );
    }

    @Test
    public void nextStep() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(players), duration, null);
        Assert.assertEquals( players.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( ClassicContent.class, game.getRoundContent().getClass() );

        game.nextStep();
        Assert.assertEquals( players.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 1, game.getNbMusicsPlayed() );
        Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( ClassicContent.class, game.getRoundContent().getClass() );

        while ( !game.isFinished() )
            game.nextStep();

        Assert.assertEquals( players.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 48, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertNull( game.getRound() );
        Assert.assertNull( game.getRoundContent() );
    }

    @Test
    public void isFirstStep() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(players), duration, null);
        Assert.assertTrue( game.isFirstStep() );

        while ( !game.isFinished() ) {
            game.nextStep();
            Assert.assertFalse( game.isFirstStep() );
        }

        Assert.assertFalse( game.isFirstStep() );
    }

    @Test
    public void isLastStep() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(players), duration, null);
        Assert.assertFalse( game.isLastStep() );

        for (int i = 0; i < 46; i++) {
            game.nextStep();
            Assert.assertFalse( game.isLastStep() );
        }

        game.nextStep();
        Assert.assertTrue( game.isLastStep() );

        game.nextStep();
        Assert.assertFalse( game.isLastStep() );
    }

    @Test
    public void isFinished() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(players), duration, null);
        int nbMusic = 0;

        game.nextStep();
        nbMusic++;
        Assert.assertFalse( game.isFinished() );

        while (game.getRound() != null) {
            game.nextStep();
            nbMusic++;
        }

        Assert.assertTrue( game.isFinished() );
        Assert.assertEquals( nbMusic, game.getNbMusicsPlayed() );
    }

    @Test
    public void toStringAndEquals() {

        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);

        Game gameUn = new Game(new HashSet<>(players), duration, themes);
        Assert.assertEquals( "players=[" + players.get(0) + ", "+ players.get(1) + "], duration=NORMAL, nbMusicsPlayed=0, " +
                "nbMusicsPlayedInRound=0, round=CLASSIC, roundContent={nbMusics=20, nbPointWon=100}, themes=[ANNEES_60, ANNEES_70]", gameUn.toString() );
    }

}