package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MusicResultDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        Integer gameId = 1;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        try {
            new MusicResultDTO(null, musicDTO, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameId' est obligatoire."), e);
        }

        try {
            new MusicResultDTO(gameId, null, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
        }

        Assert.assertNotNull( new MusicResultDTO(gameId, musicDTO, null, null, null) );
    }

    @Test
    public void getterSetter() {

        Integer gameId = 1;
        Round round = Round.CLASSIC;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        MusicResultDTO musicResultDTO = new MusicResultDTO(gameId, musicDTO, null, null, null);
        Assert.assertEquals( gameId, musicResultDTO.getGameId() );
        Assert.assertEquals( musicDTO, musicResultDTO.getMusicDTO() );
        Assert.assertEquals( new ArrayList<>(), musicResultDTO.getWinners() );
        Assert.assertEquals( new ArrayList<>(), musicResultDTO.getNeutral() );
        Assert.assertEquals( new ArrayList<>(), musicResultDTO.getLoosers() );
    }

}