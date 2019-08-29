package com.myssteriion.blindtest.model.dto.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GameDTOTest extends AbstractTest {

    @Test
    public void constructor() {

        List<String> playersNames = Arrays.asList("name");
        List<PlayerDTO> players = Arrays.asList(new PlayerDTO("name"));

        Duration duration = Duration.NORMAL;


        try {
            new GameDTO(null, duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        try {
            new GameDTO(new HashSet<>(playersNames), null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'duration' est obligatoire."), e);
        }

        try {
            new GameDTO(new HashSet<>(), duration);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'playersNames' est obligatoire."), e);
        }

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( players, gameDTO.getPlayers() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getRound() );


        playersNames = Arrays.asList("name4", "name1", "name3", "name2");
        players = Arrays.asList(new PlayerDTO("name1"), new PlayerDTO("name2"), new PlayerDTO("name3"), new PlayerDTO("name4"));

        gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( players, gameDTO.getPlayers() );
    }

    @Test
    public void getterSetter() {

        List<String> playersNames = Arrays.asList("name");
        List<PlayerDTO> players = Arrays.asList( new PlayerDTO("name") );
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( players, gameDTO.getPlayers() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getRound() );
    }

    @Test
    public void nextStep() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getRound() );
        Assert.assertEquals( ClassicContent.class, gameDTO.getRoundContent().getClass() );

        gameDTO.nextStep();
        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 1, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 1, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertEquals( Round.CLASSIC, gameDTO.getRound() );
        Assert.assertEquals( ClassicContent.class, gameDTO.getRoundContent().getClass() );

        while ( !gameDTO.isFinished() )
            gameDTO.nextStep();

        Assert.assertEquals( playersNames.size(), gameDTO.getPlayers().size() );
        Assert.assertEquals( duration, gameDTO.getDuration() );
        Assert.assertEquals( 44, gameDTO.getNbMusicsPlayed() );
        Assert.assertEquals( 0, gameDTO.getNbMusicsPlayedInRound() );
        Assert.assertNull( gameDTO.getRound() );
        Assert.assertNull( gameDTO.getRoundContent() );
    }

    @Test
    public void isFirstStep() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertTrue( gameDTO.isFirstStep() );

        while ( !gameDTO.isFinished() ) {
            gameDTO.nextStep();
            Assert.assertFalse( gameDTO.isFirstStep() );
        }

        Assert.assertFalse( gameDTO.isFirstStep() );
    }

    @Test
    public void isLastStep() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertFalse( gameDTO.isLastStep() );

        for (int i = 0; i < 42; i++) {
            gameDTO.nextStep();
            Assert.assertFalse( gameDTO.isLastStep() );
        }

        gameDTO.nextStep();
        Assert.assertTrue( gameDTO.isLastStep() );

        gameDTO.nextStep();
        Assert.assertFalse( gameDTO.isLastStep() );
    }

    @Test
    public void isFinished() {

        List<String> playersNames = Arrays.asList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDTO = new GameDTO(new HashSet<>(playersNames), duration);
        int nbMusic = 0;

        gameDTO.nextStep();
        nbMusic++;
        Assert.assertFalse( gameDTO.isFinished() );

        while (gameDTO.getRound() != null) {
            gameDTO.nextStep();
            nbMusic++;
        }

        Assert.assertTrue( gameDTO.isFinished() );
        Assert.assertEquals( nbMusic, gameDTO.getNbMusicsPlayed() );
    }

    @Test
    public void toStringAndEquals() {

        List<String> playersNames = Collections.singletonList("name");
        Duration duration = Duration.NORMAL;

        GameDTO gameDtoUn = new GameDTO(new HashSet<>(playersNames), duration);
        Assert.assertEquals( "players=[name=name, score=0, turnToChoose=false], duration=NORMAL, nbMusicsPlayed=0, nbMusicsPlayedInRound=0, round=CLASSIC, roundContent={nbMusics=20, nbPointWon=100}", gameDtoUn.toString() );
    }

}