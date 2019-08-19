package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.model.dto.game.MusicResultDTO;
import com.myssteriion.blindtest.model.dto.game.PlayerDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

        classicContent = new ClassicContent(20,  100);
        Assert.assertEquals( 20, classicContent.getNbMusics() );
        Assert.assertEquals( 100, classicContent.getNbPointWon() );
    }

    @Test
    public void apply() {

        int nbPointWon = 100;
        ClassicContent classicContent = new ClassicContent(20,  nbPointWon);

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDto = new GameDTO(playersNames, Duration.NORMAL);

        Integer gameId = 1;
        MusicDTO musicDTO = new MusicDTO("name", Theme.ANNEES_80);
        MusicResultDTO musicResultDto = new MusicResultDTO(gameId, musicDTO, playersNames, null, null, null, null);


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


        GameDTO actual = classicContent.apply(gameDto, null);
        Assert.assertEquals( nbPointWon, actual.getPlayers().get(0).getScore() );
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        GameDTO gameDTO = new GameDTO(playersNames, Duration.NORMAL);

        ClassicContent classicContent = new ClassicContent(20,  100);

        try {
            classicContent.isFinished(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameDto' est obligatoire."), e);
        }

        Assert.assertFalse( classicContent.isFinished(gameDTO) );

    }

    @Test
    public void toStringAndEquals() {

        ClassicContent classicContent = new ClassicContent(20,  100);
        Assert.assertEquals( "nbMusics=20, nbMusics=100", classicContent.toString() );
    }

}