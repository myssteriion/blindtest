package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Round;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class RecoveryContentTest extends AbstractTest {

    @Test
    public void constructors() {

        RecoveryContent recoveryContent = new RecoveryContent(-1,  -2);
        Assert.assertEquals( 0, recoveryContent.getNbMusics() );
        Assert.assertEquals( 0, recoveryContent.getNbPointWon() );
    }

    @Test
    public void getterSetter() {

        RecoveryContent recoveryContent = new RecoveryContent(-1,  -2);
        Assert.assertEquals( 0, recoveryContent.getNbMusics() );
        Assert.assertEquals( 0, recoveryContent.getNbPointWon() );

        recoveryContent = new RecoveryContent(5,  100);
        Assert.assertEquals( 5, recoveryContent.getNbMusics() );
        Assert.assertEquals( 100, recoveryContent.getNbPointWon() );
    }

    @Test
    public void apply() {

        List<String> playersNames = Collections.singletonList("name");
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO("name")),
                new Player(new ProfileDTO("name1")));
        Game game = new Game(new HashSet<>(players), Duration.NORMAL, null, null, ConnectionMode.OFFLINE);

        Integer gameId = 1;
        MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        MusicResult musicResult = new MusicResult(gameId, musicDto, playersNames, null, null, null);

        for (int i = 0; i < 72; i++)
            game.nextStep();

        Assert.assertSame( Round.RECOVERY, game.getRound() );
        RecoveryContent recoveryContent = (RecoveryContent) game.getRoundContent();

        try {
            recoveryContent.apply(null, musicResult);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }

        try {
            recoveryContent.apply(game, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }


        Game actual = recoveryContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 30, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, playersNames, null, null);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 60, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, playersNames, playersNames, null, null);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 120, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, null, playersNames, null);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 120, actual.getPlayers().get(0).getScore() );

        musicResult = new MusicResult(gameId, musicDto, null, null, null, playersNames);
        actual = recoveryContent.apply(game, musicResult);
        game.nextStep();
        Assert.assertEquals( 60, actual.getPlayers().get(0).getScore() );
    }

}