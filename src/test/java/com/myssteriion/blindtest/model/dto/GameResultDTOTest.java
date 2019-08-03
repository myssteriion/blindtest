package com.myssteriion.blindtest.model.dto;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.GameResultType;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameResultDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        GameResultType type = GameResultType.NORMAL;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        try {
            new GameResultDTO(null, musicDTO, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'type' est obligatoire."), e);
        }

        try {
            new GameResultDTO(type, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
        }

        Assert.assertNotNull( new GameResultDTO(type, musicDTO, null, null) );



        GameResultDTO gameResultDTO = new GameResultDTO(type, musicDTO, null, null);
        Assert.assertEquals( GameResultType.NORMAL, gameResultDTO.getType() );
        Assert.assertEquals( musicDTO, gameResultDTO.getMusicDTO() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getWinners() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getLoosers() );
    }

    @Test
    public void getterSetter() {

        GameResultType type = GameResultType.NORMAL;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        GameResultDTO gameResultDTO = new GameResultDTO(type, musicDTO, null, null);
        Assert.assertEquals( GameResultType.NORMAL, gameResultDTO.getType() );
        Assert.assertEquals( musicDTO, gameResultDTO.getMusicDTO() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getWinners() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getLoosers() );
    }

}