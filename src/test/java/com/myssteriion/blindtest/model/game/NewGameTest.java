package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GameMode;
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
            new NewGame(null, duration, null, GameMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new NewGame(new HashSet<>(playersNames), null, null, GameMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration, null, GameMode.OFFLINE) );
        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration, themes, GameMode.OFFLINE) );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);


        NewGame newGame = new NewGame(new HashSet<>(playersNames), duration, null, GameMode.OFFLINE);
        Assert.assertEquals( new HashSet<>(playersNames), newGame.getPlayersNames() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertEquals( Theme.getSortedTheme(), newGame.getThemes() );
        Assert.assertEquals( GameMode.OFFLINE, newGame.getGameMode() );

        newGame = new NewGame(new HashSet<>(playersNames), duration, themes, GameMode.ONLINE);
        Assert.assertEquals( new HashSet<>(playersNames), newGame.getPlayersNames() );
        Assert.assertEquals( duration, newGame.getDuration() );
        Assert.assertEquals( themes, newGame.getThemes() );
        Assert.assertEquals( GameMode.ONLINE, newGame.getGameMode() );
    }

    @Test
    public void toStringAndEquals() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;
        List<Theme> themes = Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70);

        NewGame gameDtoUn = new NewGame(new HashSet<>(playersNames), duration, themes, GameMode.OFFLINE);
        Assert.assertEquals( "playersNames=[name], duration=NORMAL, themes=[ANNEES_60, ANNEES_70], gameMode=OFFLINE", gameDtoUn.toString() );
    }
    
}