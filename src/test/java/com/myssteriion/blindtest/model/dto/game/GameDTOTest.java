package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Arrays.asList("name");

        try {
            new GameDTO(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new GameDTO( new ArrayList<>() );
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        GameDTO gameDTO = new GameDTO(playersNames);
        Assert.assertNotNull(gameDTO);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getCurrent() );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");

        GameDTO gameDTO = new GameDTO(playersNames);
        Assert.assertNotNull(gameDTO);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getCurrent() );
    }

    @Test
    public void next() {

        List<String> playersNames = Arrays.asList("name");

        GameDTO gameDTO = new GameDTO(playersNames);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getCurrent() );

        gameDTO.next();
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( 1, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 1, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getCurrent() );

        for (int i = 1; i < Round.CLASSIC.getNbMusics(); i++)
            gameDTO.next();

        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( Round.CLASSIC.getNbMusics(), gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        assertNull(gameDTO.getCurrent());
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");

        GameDTO gameDTO = new GameDTO(playersNames);
        gameDTO.next();
        Assert.assertFalse( gameDTO.isFinished() );

        for (int i = 1; i < Round.CLASSIC.getNbMusics(); i++)
            gameDTO.next();

        Assert.assertTrue( gameDTO.isFinished() );
    }

    @Test
    public void toStringAndEquals() {

        String name = "name";
        List<String> playersNames = Collections.singletonList("name");
        GameDTO gameDtoUn = new GameDTO(playersNames);

        Assert.assertEquals( "players=[name=name, score=0], nbMusicsPlayed=0, nbMusicsPlayedInRound=0, current=CLASSIC", gameDtoUn.toString() );
    }

}