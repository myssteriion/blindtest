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

public class ClassicContentTest extends AbstractTest {

    @Test
    public void constructors() {

        ClassicContent classicContent = new ClassicContent(-1,  -2);
        Assert.assertEquals( 0, classicContent.getNbMusics() );
        Assert.assertEquals( 0, classicContent.getNbPointWon() );
    }

    @Test
    public void getterSetter() {

        ClassicContent classicContent = new ClassicContent(-1,  -2);
        Assert.assertEquals( 0, classicContent.getNbMusics() );
        Assert.assertEquals( 0, classicContent.getNbPointWon() );

        classicContent = new ClassicContent(5,  100);
        Assert.assertEquals( 5, classicContent.getNbMusics() );
        Assert.assertEquals( 100, classicContent.getNbPointWon() );
    }

    @Test
    public void apply() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
        MusicResultDTO musicResultDto = new MusicResultDTO(gameId, musicDto, playersNames, null, null, null, null);

        Assert.assertSame( Round.CLASSIC, gameDto.getRound() );
        ClassicContent classicContent = (ClassicContent) gameDto.getRoundContent();

        try {
            classicContent.apply(null, musicResultDto);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        try {
            classicContent.apply(gameDto, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResultDto' est obligatoire."), e);
        }


        GameDTO actual = classicContent.apply(gameDto, musicResultDto);
        gameDto.nextStep();
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        Assert.assertSame( Round.CLASSIC, gameDto.getRound() );
        ClassicContent classicContent = (ClassicContent) gameDto.getRoundContent();;

        try {
            classicContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        Assert.assertFalse( classicContent.isFinished(gameDto) );

        for (int i = 0; i < 4; i++) {
            gameDto.nextStep();
            Assert.assertFalse( classicContent.isFinished(gameDto) );
        }

        // le isFinish ne peut etre testé à "true" car le nextStep remet à 0 le "nbMusicsPlayedInRound"
        gameDto.nextStep();
        Assert.assertEquals( 0, gameDto.getNbMusicsPlayedInRound() );
    }

    @Test
    public void isLast() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        Assert.assertSame( Round.CLASSIC, gameDto.getRound() );
        ClassicContent classicContent = (ClassicContent) gameDto.getRoundContent();

        Assert.assertFalse( classicContent.isLastMusic(gameDto) );

        for (int i = 0; i < 3; i++) {
            gameDto.nextStep();
            Assert.assertFalse( classicContent.isLastMusic(gameDto) );
        }

        gameDto.nextStep();
        Assert.assertTrue( classicContent.isLastMusic(gameDto) );

        gameDto.nextStep();
        Assert.assertFalse( classicContent.isLastMusic(gameDto) );
    }

    @Test
    public void toStringAndEquals() {

        ClassicContent classicContent = new ClassicContent(5,  100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100", classicContent.toString() );
    }

}