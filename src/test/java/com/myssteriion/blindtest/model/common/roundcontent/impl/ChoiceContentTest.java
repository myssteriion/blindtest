package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
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

        choiceContent = new ChoiceContent(20,  100, 50, -50);
        Assert.assertEquals( 20, choiceContent.getNbMusics() );
        Assert.assertEquals( 100, choiceContent.getNbPointWon() );
        Assert.assertEquals( 50, choiceContent.getNbPointBonusWon() );
        Assert.assertEquals( -50, choiceContent.getNbPointMalusLoose() );
    }

    @Test
    public void apply() {

        int nbPointWon = 100;
        int nbPointBonusWon = 50;
        int nbPointMalusLoose = -50;
        ChoiceContent choiceContent = new ChoiceContent(20,  nbPointWon, nbPointBonusWon, nbPointMalusLoose);

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(playersNames, Duration.NORMAL);

        Integer gameId = 1;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);
        MusicResultDTO musicResultDto = new MusicResultDTO(gameId, musicDTO, playersNames, playersNames, null, null, null);


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
        Assert.assertEquals( nbPointWon + nbPointBonusWon, actual.getPlayers().get(0).getScore() );


        musicResultDto = new MusicResultDTO(gameId, musicDTO, null, null, null, null, playersNames);
        actual = choiceContent.apply(gameDto, musicResultDto);
        Assert.assertEquals( nbPointWon + nbPointBonusWon + nbPointMalusLoose, actual.getPlayers().get(0).getScore() );
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDTO = new GameDTO(playersNames, Duration.NORMAL);

        ChoiceContent choiceContent = new ChoiceContent(20,  100, 50, -50);

        try {
            choiceContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        Assert.assertFalse( choiceContent.isFinished(gameDTO) );
    }

    @Test
    public void toStringAndEquals() {

        ChoiceContent choiceContent = new ChoiceContent(20,  100, 50, -50);
        Assert.assertEquals( "nbMusics=20, nbPointWon=100, nbPointBonusWon=50, nbPointMalusLoose=-50", choiceContent.toString() );
    }

}