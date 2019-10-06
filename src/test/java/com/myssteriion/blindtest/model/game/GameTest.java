package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Arrays.asList("name");
        List<Player> players = Arrays.asList(new Player("name"));

        Duration duration = Duration.NORMAL;


        try {
            new Game(null, duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new Game(new HashSet<>(playersNames), null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        try {
            new Game(new HashSet<>(), duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        Game game = new Game(new HashSet<>(playersNames), duration);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( Effect.NONE, game.getNextEffect() );


        playersNames = Arrays.asList("name4", "name1", "name3", "name2");
        players = Arrays.asList(new Player("name1"), new Player("name2"), new Player("name3"), new Player("name4"));

        game = new Game(new HashSet<>(playersNames), duration);
        Assert.assertEquals( players, game.getPlayers() );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");
        List<Player> players = Arrays.asList( new Player("name") );
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(playersNames), duration);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( Effect.NONE, game.getNextEffect() );
    }

    @Test
    public void nextStep() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(playersNames), duration);
        Assert.assertEquals( playersNames.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( ClassicContent.class, game.getRoundContent().getClass() );

        game.nextStep();
        Assert.assertEquals( playersNames.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 1, game.getNbMusicsPlayed() );
        Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( ClassicContent.class, game.getRoundContent().getClass() );

        while ( !game.isFinished() )
            game.nextStep();

        Assert.assertEquals( playersNames.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 44, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertNull( game.getRound() );
        Assert.assertNull( game.getRoundContent() );
    }

    @Test
    public void isFirstStep() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(playersNames), duration);
        Assert.assertTrue( game.isFirstStep() );

        while ( !game.isFinished() ) {
            game.nextStep();
            Assert.assertFalse( game.isFirstStep() );
        }

        Assert.assertFalse( game.isFirstStep() );
    }

    @Test
    public void isLastStep() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(playersNames), duration);
        Assert.assertFalse( game.isLastStep() );

        for (int i = 0; i < 42; i++) {
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

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        Game game = new Game(new HashSet<>(playersNames), duration);
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

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;

        Game gameUn = new Game(new HashSet<>(playersNames), duration);
        Assert.assertEquals( "players=[name=name, score=0, turnToChoose=false], duration=NORMAL, nbMusicsPlayed=0, nbMusicsPlayedInRound=0, round=CLASSIC, roundContent={nbMusics=20, nbPointWon=100}, nextEffect=NONE", gameUn.toString() );
    }

}