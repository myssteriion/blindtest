package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GameTest extends AbstractTest {
    
    @Test
    public void constructor() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        try {
            new Game(null, duration, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'players' est obligatoire."), e);
        }
        
        try {
            new Game(new HashSet<>(players), null, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }
        
        try {
            new Game(new HashSet<>(), duration, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'players' est obligatoire."), e);
        }
        
        try {
            new Game(new HashSet<>(Collections.singletonList(new Player(new ProfileDTO("name")))), duration, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("2 players at minimum"), e);
        }
        
        try {
            new Game(new HashSet<>(
                    Arrays.asList(
                            new Player(new ProfileDTO("name")),
                            new Player(new ProfileDTO("name1")),
                            new Player(new ProfileDTO("name2")),
                            new Player(new ProfileDTO("name3")),
                            new Player(new ProfileDTO("name4"))
                    )
            ), duration, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("4 players at maximum"), e);
        }
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( Theme.getSortedTheme(), game.getThemes() );
        Assert.assertEquals( Effect.getSortedEffect(), game.getEffects() );
        Assert.assertEquals( ConnectionMode.OFFLINE, game.getConnectionMode() );
        
        
        players = Arrays.asList(
                new Player(new ProfileDTO("name4")),
                new Player(new ProfileDTO("name1")),
                new Player(new ProfileDTO("name3")),
                new Player(new ProfileDTO("name2")));
        
        game = new Game(new HashSet<>(players), duration, false, themes, effects, ConnectionMode.OFFLINE);
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
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertFalse( game.isSameProbability() );
        Assert.assertEquals( Effect.getSortedEffect(), game.getEffects() );
        Assert.assertEquals( ConnectionMode.OFFLINE, game.getConnectionMode() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( Theme.getSortedTheme(), game.getThemes() );
        
        game = new Game(new HashSet<>(players), duration, true, themes, effects, ConnectionMode.ONLINE);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertTrue( game.isSameProbability() );
        Assert.assertEquals( themes, game.getThemes() );
        Assert.assertEquals( effects, game.getEffects() );
        Assert.assertEquals( ConnectionMode.ONLINE, game.getConnectionMode() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
    }
    
    @Test
    public void toStringAndEquals() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        Game gameUn = new Game(new HashSet<>(players), duration, false, themes, effects, ConnectionMode.OFFLINE);
        Assert.assertEquals( "players=[" + players.get(0) + ", "+ players.get(1) + "], duration=NORMAL, sameProbability=false, themes=[ANNEES_60, ANNEES_70], effects=[NONE, SPEED]" +
                ", connectionMode=OFFLINE, listenedMusics={}, nbMusicsPlayed=0, nbMusicsPlayedInRound=0, round=CLASSIC, roundContent={nbMusics=20, nbPointWon=100}", gameUn.toString() );
    }
    
    @Test
    public void incrementListenedMusics() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
        
        game.incrementListenedMusics(Theme.ANNEES_60);
        Map<Theme, Integer> foundMusics = game.getListenedMusics();
        Assert.assertEquals( new Integer(1), foundMusics.get(Theme.ANNEES_60) );
        
        game.incrementListenedMusics(Theme.ANNEES_60);
        foundMusics = game.getListenedMusics();
        Assert.assertEquals( new Integer(2), foundMusics.get(Theme.ANNEES_60) );
    }
    
    @Test
    public void nextStep() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
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
        Assert.assertEquals( 82, game.getNbMusicsPlayed() );
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
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
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
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
        Assert.assertFalse( game.isLastStep() );
        
        for (int i = 0; i < 80; i++) {
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
        
        Game game = new Game(new HashSet<>(players), duration, false, null, null, ConnectionMode.OFFLINE);
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
    
}