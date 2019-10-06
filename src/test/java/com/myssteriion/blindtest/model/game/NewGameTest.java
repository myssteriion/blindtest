package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class NewGameTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;


        try {
            new NewGame(null, duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new NewGame(new HashSet<>(playersNames), null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        Assert.assertNotNull( new NewGame(new HashSet<>(playersNames), duration) );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        NewGame newGame = new NewGame(new HashSet<>(playersNames), duration);
        Assert.assertEquals( new HashSet<>(playersNames), newGame.getPlayersNames() );
        Assert.assertEquals( duration, newGame.getDuration() );
    }

    @Test
    public void toStringAndEquals() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        NewGame gameDtoUn = new NewGame(new HashSet<>(playersNames), duration);
        Assert.assertEquals( "playersNames=[name], duration=NORMAL", gameDtoUn.toString() );
    }
    
}