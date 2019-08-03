package com.myssteriion.blindtest.model.dto.game;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");

        GameDTO gameDTO = new GameDTO(playersNames);
        Assert.assertNotNull(gameDTO);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
    }

}