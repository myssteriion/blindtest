package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.NumMusic;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameResultDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        NumMusic type = NumMusic.NORMAL;
        Round round = Round.CLASSIC;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        try {
            new GameResultDTO(null, round, musicDTO, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'numMusic' est obligatoire."), e);
        }

        try {
            new GameResultDTO(type, null, musicDTO, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'round' est obligatoire."), e);
        }

        try {
            new GameResultDTO(type, round, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
        }

        Assert.assertNotNull( new GameResultDTO(type, round, musicDTO, null, null) );



        GameResultDTO gameResultDTO = new GameResultDTO(type, round, musicDTO, null, null);
        Assert.assertEquals( NumMusic.NORMAL, gameResultDTO.getNumMusic() );
        Assert.assertEquals( musicDTO, gameResultDTO.getMusicDTO() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getWinners() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getLoosers() );
    }

    @Test
    public void getterSetter() {

        NumMusic type = NumMusic.NORMAL;
        Round round = Round.CLASSIC;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        GameResultDTO gameResultDTO = new GameResultDTO(type, round, musicDTO, null, null);
        Assert.assertEquals( NumMusic.NORMAL, gameResultDTO.getNumMusic() );
        Assert.assertEquals( Round.CLASSIC, gameResultDTO.getRound() );
        Assert.assertEquals( musicDTO, gameResultDTO.getMusicDTO() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getWinners() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getLoosers() );
    }

}