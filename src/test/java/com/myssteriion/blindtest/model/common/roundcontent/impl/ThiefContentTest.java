package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ThiefContentTest extends AbstractTest {

    @Test
    public void constructors() {

        ThiefContent thiefContent = new ThiefContent(-1,  -2, 3);
        Assert.assertEquals( 0, thiefContent.getNbMusics() );
        Assert.assertEquals( 0, thiefContent.getNbPointWon() );
        Assert.assertEquals( 0, thiefContent.getNbPointLoose() );
    }

    @Test
    public void getterSetter() {

        ThiefContent thiefContent = new ThiefContent(-1,  -2, 3);
        Assert.assertEquals( 0, thiefContent.getNbMusics() );
        Assert.assertEquals( 0, thiefContent.getNbPointWon() );
        Assert.assertEquals( 0, thiefContent.getNbPointLoose() );

        thiefContent = new ThiefContent(5,  100, -100);
        Assert.assertEquals( 5, thiefContent.getNbMusics() );
        Assert.assertEquals( 100, thiefContent.getNbPointWon() );
        Assert.assertEquals( -100, thiefContent.getNbPointLoose() );
    }

    @Test
    public void apply() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
        MusicResultDTO musicResultDto = new MusicResultDTO(gameId, musicDto, playersNames, null, null, null, null);

        for (int i = 0; i < 10; i++)
            gameDto.nextStep();

        Assert.assertSame( Round.THIEF, gameDto.getRound() );
        ThiefContent thiefContent = (ThiefContent) gameDto.getRoundContent();

        try {
            thiefContent.apply(null, musicResultDto);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        try {
            thiefContent.apply(gameDto, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResultDto' est obligatoire."), e);
        }


        GameDTO actual = thiefContent.apply(gameDto, musicResultDto);
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );


        musicResultDto = new MusicResultDTO(gameId, musicDto, null, null, null, playersNames, null);
        actual = thiefContent.apply(gameDto, musicResultDto);
        gameDto.nextStep();
        Assert.assertEquals( 0, actual.getPlayers().get(0).getScore() );
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        for (int i = 0; i < 10; i++)
            gameDto.nextStep();

        Assert.assertSame( Round.THIEF, gameDto.getRound() );
        ThiefContent thiefContent = (ThiefContent) gameDto.getRoundContent();

        try {
            thiefContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        Assert.assertFalse( thiefContent.isFinished(gameDto) );
    }

    @Test
    public void isLast() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        for (int i = 0; i < 10; i++)
            gameDto.nextStep();

        Assert.assertSame( Round.THIEF, gameDto.getRound() );
        ThiefContent thiefContent = (ThiefContent) gameDto.getRoundContent();

        Assert.assertFalse( thiefContent.isLastMusic(gameDto) );

        for (int i = 0; i < 3; i++) {
            gameDto.nextStep();
            Assert.assertFalse( thiefContent.isLastMusic(gameDto) );
        }

        gameDto.nextStep();
        Assert.assertTrue( thiefContent.isLastMusic(gameDto) );

        gameDto.nextStep();
        Assert.assertFalse( thiefContent.isLastMusic(gameDto) );
    }

    @Test
    public void toStringAndEquals() {

        ThiefContent thiefContent = new ThiefContent(5,  100, -100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointLoose=-100", thiefContent.toString() );
    }

}