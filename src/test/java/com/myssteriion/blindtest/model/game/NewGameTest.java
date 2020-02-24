package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class NewGameTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);

        try {
            new NewGame(null, duration, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new NewGame(new HashSet<>(playersNames), null, false, null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration, false, null, null, ConnectionMode.OFFLINE) );
        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration, false, themes, effects, ConnectionMode.OFFLINE) );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);

        NewGame newGame = new NewGame(new HashSet<>(playersNames), duration, false, null, null, ConnectionMode.OFFLINE);
        Assert.assertEquals( new HashSet<>(playersNames), newGame.getPlayersNames() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertEquals( Theme.getSortedTheme(), newGame.getThemes() );
        Assert.assertEquals( ConnectionMode.OFFLINE, newGame.getConnectionMode() );

        newGame = new NewGame(new HashSet<>(playersNames), duration, false, themes, effects, ConnectionMode.ONLINE);
        Assert.assertEquals( new HashSet<>(playersNames), newGame.getPlayersNames() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertEquals( themes, newGame.getThemes() );
        Assert.assertEquals( ConnectionMode.ONLINE, newGame.getConnectionMode() );
    }

    @Test
    public void toStringAndEquals() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);
        List<Effect> effects = Arrays.asList(Effect.NONE, Effect.SPEED);

        NewGame gameDtoUn = new NewGame(new HashSet<>(playersNames), duration, false, themes, effects, ConnectionMode.OFFLINE);
        Assert.assertEquals( "playersNames=[name], duration=NORMAL, sameProbability=false, themes=[ANNEES_60, ANNEES_70], effects=[NONE, SPEED], connectionMode=OFFLINE", gameDtoUn.toString() );
    }
    
}