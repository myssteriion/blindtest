package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameTest extends AbstractTest {
    
    @Test
    public void getterSetter() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertNull( game.getEffects() );
        Assert.assertEquals( ConnectionMode.OFFLINE, game.getConnectionMode() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertNull( game.getThemes() );
        
        game = new Game(players, duration, themes, effects, ConnectionMode.ONLINE, roundContentProperties);
        Assert.assertEquals( players, game.getPlayers() );
        Assert.assertEquals( duration, game.getDuration() );
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
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);
        
        Game gameUn = new Game(players, duration, themes, effects, ConnectionMode.OFFLINE, roundContentProperties);
        Assert.assertEquals( "players=[" + players.get(0) + ", "+ players.get(1) + "], duration=NORMAL, themes=[ANNEES_60, ANNEES_70], effects=[NONE, SPEED]" +
                ", connectionMode=OFFLINE, listenedMusics=null, nbMusicsPlayed=0, nbMusicsPlayedInRound=0, round=CLASSIC, roundContent={nbMusics=20, nbPointWon=100}", gameUn.toString() );
    }
    
    @Test
    public void incrementListenedMusics() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        game.incrementListenedMusics(Theme.ANNEES_60);
        Map<Theme, Integer> foundMusics = game.getListenedMusics();
        Assert.assertEquals( Integer.valueOf(1), foundMusics.get(Theme.ANNEES_60) );
        
        game.incrementListenedMusics(Theme.ANNEES_60);
        foundMusics = game.getListenedMusics();
        Assert.assertEquals( Integer.valueOf(2), foundMusics.get(Theme.ANNEES_60) );
    }
    
    @Test
    public void nextStep() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        Assert.assertEquals( players.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 0, game.getNbMusicsPlayed() );
        Assert.assertEquals( 0, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( ClassicContent.class, game.getRoundContent().getClass() );
        
        game.nextStep(roundContentProperties);
        Assert.assertEquals( players.size(), game.getPlayers().size() );
        Assert.assertEquals( duration, game.getDuration() );
        Assert.assertEquals( 1, game.getNbMusicsPlayed() );
        Assert.assertEquals( 1, game.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, game.getRound() );
        Assert.assertEquals( ClassicContent.class, game.getRoundContent().getClass() );
        
        while ( !game.isFinished() )
            game.nextStep(roundContentProperties);
        
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
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        Assert.assertTrue( game.isFirstStep() );
        
        while ( !game.isFinished() ) {
            game.nextStep(roundContentProperties);
            Assert.assertFalse( game.isFirstStep() );
        }
        
        Assert.assertFalse( game.isFirstStep() );
    }
    
    @Test
    public void isLastStep() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        Assert.assertFalse( game.isLastStep() );
        
        for (int i = 0; i < 80; i++) {
            game.nextStep(roundContentProperties);
            Assert.assertFalse( game.isLastStep() );
        }
        
        game.nextStep(roundContentProperties);
        Assert.assertTrue( game.isLastStep() );
        
        game.nextStep(roundContentProperties);
        Assert.assertFalse( game.isLastStep() );
    }
    
    @Test
    public void isFinished() {
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name1")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        int nbMusic = 0;
        
        game.nextStep(roundContentProperties);
        nbMusic++;
        Assert.assertFalse( game.isFinished() );
        
        while (game.getRound() != null) {
            game.nextStep(roundContentProperties);
            nbMusic++;
        }
        
        Assert.assertTrue( game.isFinished() );
        Assert.assertEquals( nbMusic, game.getNbMusicsPlayed() );
    }
    
}