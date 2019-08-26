package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class NewGameDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;


        try {
            new NewGameDTO(null, duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new NewGameDTO(new HashSet<>(playersNames), null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        Assert.assertNotNull( new NewGameDTO(new HashSet<>(playersNames), duration) );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        NewGameDTO newGameDto = new NewGameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( new HashSet<>(playersNames), newGameDto.getPlayersNames() );
        Assert.assertEquals( duration, newGameDto.getDuration() );
    }

    @Test
    public void toStringAndEquals() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        NewGameDTO gameDtoUn = new NewGameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( "playersNames=[name], duration=NORMAL", gameDtoUn.toString() );
    }
    
}