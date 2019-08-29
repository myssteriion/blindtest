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

public class ChoiceContentTest extends AbstractTest {

    @Test
    public void constructors() {

        ChoiceContent choiceContent = new ChoiceContent(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choiceContent.getNbMusics() );
        Assert.assertEquals( 0, choiceContent.getNbPointWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointBonusWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointMalusLoose() );
    }

    @Test
    public void getterSetter() {

        ChoiceContent choiceContent = new ChoiceContent(-1,  -2, -2, 3);
        Assert.assertEquals( 0, choiceContent.getNbMusics() );
        Assert.assertEquals( 0, choiceContent.getNbPointWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointBonusWon() );
        Assert.assertEquals( 0, choiceContent.getNbPointMalusLoose() );

        choiceContent = new ChoiceContent(5,  100, 50, -50);
        Assert.assertEquals( 5, choiceContent.getNbMusics() );
        Assert.assertEquals( 100, choiceContent.getNbPointWon() );
        Assert.assertEquals( 50, choiceContent.getNbPointBonusWon() );
        Assert.assertEquals( -50, choiceContent.getNbPointMalusLoose() );
    }

    @Test
    public void apply() {

        List<String> playersNames = Arrays.asList("name", "name3", "name2");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
        MusicResultDTO musicResultDto = new MusicResultDTO(gameId, musicDto, playersNames, playersNames, null, null, null);

        for (int i = 0; i < 20; i++)
            gameDto.nextStep();

        Assert.assertSame( Round.CHOICE, gameDto.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) gameDto.getRoundContent();

        Assert.assertTrue( gameDto.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( gameDto.getPlayers().get(1).isTurnToChoose() );
        Assert.assertFalse( gameDto.getPlayers().get(2).isTurnToChoose() );


        try {
            choiceContent.apply(null, musicResultDto);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        try {
            choiceContent.apply(gameDto, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResultDto' est obligatoire."), e);
        }

        GameDTO actual = choiceContent.apply(gameDto, musicResultDto);
        Assert.assertEquals( 150, actual.getPlayers().get(0).getScore() );


        musicResultDto = new MusicResultDTO(gameId, musicDto, null, null, null, null, playersNames);
        actual = choiceContent.apply(gameDto, musicResultDto);
        gameDto.nextStep();
        Assert.assertEquals( 100, actual.getPlayers().get(0).getScore() );


        for (int i = 2; i < 12; i++) {

            choiceContent.apply(gameDto, musicResultDto);
            gameDto.nextStep();

            if (i%3 == 0) {
                Assert.assertTrue( gameDto.getPlayers().get(0).isTurnToChoose() );
                Assert.assertFalse( gameDto.getPlayers().get(1).isTurnToChoose() );
                Assert.assertFalse( gameDto.getPlayers().get(2).isTurnToChoose() );
            }
            else if (i%3 == 1) {
                Assert.assertFalse( gameDto.getPlayers().get(0).isTurnToChoose() );
                Assert.assertTrue( gameDto.getPlayers().get(1).isTurnToChoose() );
                Assert.assertFalse( gameDto.getPlayers().get(2).isTurnToChoose() );
            }
            else {
                Assert.assertFalse( gameDto.getPlayers().get(0).isTurnToChoose() );
                Assert.assertFalse( gameDto.getPlayers().get(1).isTurnToChoose() );
                Assert.assertTrue( gameDto.getPlayers().get(2).isTurnToChoose() );
            }
        }
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        for (int i = 0; i < 20; i++)
            gameDto.nextStep();

        Assert.assertSame( Round.CHOICE, gameDto.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) gameDto.getRoundContent();

        try {
            choiceContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        Assert.assertFalse( choiceContent.isFinished(gameDto) );
    }

    @Test
    public void isLast() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(new HashSet<>(playersNames), Duration.NORMAL);

        for (int i = 0; i < 20; i++)
            gameDto.nextStep();

        Assert.assertSame( Round.CHOICE, gameDto.getRound() );
        ChoiceContent choiceContent = (ChoiceContent) gameDto.getRoundContent();

        Assert.assertFalse( choiceContent.isLastMusic(gameDto) );

        for (int i = 0; i < 2; i++) {
            gameDto.nextStep();
            Assert.assertFalse( choiceContent.isLastMusic(gameDto) );
        }

        gameDto.nextStep();
        Assert.assertTrue( choiceContent.isLastMusic(gameDto) );

        gameDto.nextStep();
        Assert.assertFalse( choiceContent.isLastMusic(gameDto) );
    }

    @Test
    public void toStringAndEquals() {

        ChoiceContent choiceContent = new ChoiceContent(5,  100, 50, -50);
        Assert.assertEquals( "nbMusics=5, nbPointWon=100, nbPointBonusWon=50, nbPointMalusLoose=-50", choiceContent.toString() );
    }

}