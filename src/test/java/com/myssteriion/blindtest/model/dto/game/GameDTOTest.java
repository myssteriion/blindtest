package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;


        try {
            new GameDTO(null, duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new GameDTO(playersNames, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        try {
            new GameDTO(new ArrayList<>(), duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        GameDTO gameDTO = new GameDTO(playersNames, duration);
        Assert.assertNotNull(gameDTO);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getCurrent() );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(playersNames, duration);
        Assert.assertNotNull(gameDTO);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getCurrent() );
    }

    @Test
    public void next() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(playersNames, duration);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.getFirst(), gameDTO.getCurrent() );

        gameDTO.next();
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 1, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 1, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.getFirst(), gameDTO.getCurrent() );

        for (int i = 1; i < Round.getFirst().getNbMusics(); i++)
            gameDTO.next();

        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( Round.getFirst().getNbMusics(), gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.getFirst().next(), gameDTO.getCurrent() );
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(playersNames, duration);
        int nbMusic = 0;

        gameDTO.next();
        nbMusic++;
        Assert.assertFalse( gameDTO.isFinished() );

        while (gameDTO.getCurrent() != null) {
            gameDTO.next();
            nbMusic++;
        }

        Assert.assertTrue( gameDTO.isFinished() );
        Assert.assertEquals( nbMusic, gameDTO.getNbMusicsPlayed() );
    }

    @Test
    public void toStringAndEquals() {

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDtoUn = new GameDTO(playersNames, duration);
        Assert.assertEquals( "players=[name=name, score=0], duration=NORMAL, nbMusicsPlayed=0, nbMusicsPlayedInRound=0, current=CLASSIC", gameDtoUn.toString() );
    }

}