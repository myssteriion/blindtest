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

        Integer gameId = 1;
        NumMusic numMusic = NumMusic.NORMAL;
        Round round = Round.CLASSIC;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        try {
            new GameResultDTO(null, numMusic, round, musicDTO, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameId' est obligatoire."), e);
        }

        try {
            new GameResultDTO(gameId, null, round, musicDTO, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'numMusic' est obligatoire."), e);
        }

        try {
            new GameResultDTO(gameId, numMusic, null, musicDTO, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'round' est obligatoire."), e);
        }

        try {
            new GameResultDTO(gameId, numMusic, round, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
        }

        Assert.assertNotNull( new GameResultDTO(gameId, numMusic, round, musicDTO, null, null) );
    }

    @Test
    public void getterSetter() {

        Integer gameId = 1;
        NumMusic type = NumMusic.NORMAL;
        Round round = Round.CLASSIC;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);


        GameResultDTO gameResultDTO = new GameResultDTO(gameId, type, round, musicDTO, null, null);
        Assert.assertEquals( gameId, gameResultDTO.getGameId() );
        Assert.assertEquals( NumMusic.NORMAL, gameResultDTO.getNumMusic() );
        Assert.assertEquals( Round.CLASSIC, gameResultDTO.getRound() );
        Assert.assertEquals( musicDTO, gameResultDTO.getMusicDTO() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getWinners() );
        Assert.assertEquals( new ArrayList<>(), gameResultDTO.getLoosers() );
    }

}