package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
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


        try {
            new NewGame(null, duration, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new NewGame(new HashSet<>(playersNames), null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration, null, ConnectionMode.OFFLINE) );
        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration, themes, ConnectionMode.OFFLINE) );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);


        NewGame newGame = new NewGame(new HashSet<>(playersNames), duration, null, ConnectionMode.OFFLINE);
        Assert.assertEquals( new HashSet<>(playersNames), newGame.getPlayersNames() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertEquals( Theme.getSortedTheme(), newGame.getThemes() );
        Assert.assertEquals( ConnectionMode.OFFLINE, newGame.getConnectionMode() );

        newGame = new NewGame(new HashSet<>(playersNames), duration, themes, ConnectionMode.ONLINE);
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

        NewGame gameDtoUn = new NewGame(new HashSet<>(playersNames), duration, themes, ConnectionMode.OFFLINE);
        Assert.assertEquals( "playersNames=[name], duration=NORMAL, themes=[ANNEES_60, ANNEES_70], connectionMode=OFFLINE", gameDtoUn.toString() );
    }
    
}