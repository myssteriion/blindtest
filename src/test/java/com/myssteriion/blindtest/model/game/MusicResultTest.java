package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MusicResultTest extends AbstractTest {

    @Test
    public void constructor() {

        Integer gameId = 1;
        MusicDTO music = new MusicDTO("name", Theme.ANNEES_80);


        try {
            new MusicResult(null, music, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'gameId' est obligatoire."), e);
        }

        try {
            new MusicResult(gameId, null, null, null, null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'music' est obligatoire."), e);
        }

        Assert.assertNotNull( new MusicResult(gameId, music, null, null, null) );
    }

    @Test
    public void getterSetter() {

        Integer gameId = 1;
        MusicDTO music = new MusicDTO("name", Theme.ANNEES_80);


        MusicResult musicResult = new MusicResult(gameId, music, null, null, null);
        Assert.assertEquals( gameId, musicResult.getGameId() );
        Assert.assertEquals( music, musicResult.getMusic() );
        Assert.assertEquals( new ArrayList<>(), musicResult.getAuthorWinners() );
        Assert.assertEquals( new ArrayList<>(), musicResult.getTitleWinners() );
        Assert.assertEquals( new ArrayList<>(), musicResult.getLosers() );
    }

    @Test
    public void toStringAndEquals() {

        Integer gameId = 1;
        MusicDTO music = new MusicDTO("name", Theme.ANNEES_80);
        MusicResult musicResult = new MusicResult(gameId, music, null, null, null);
        Assert.assertEquals( "gameId=1, music={id=null, name=name, theme=ANNEES_80, played=0, connectionMode=OFFLINE, spotifyTrackId=null, spotifyPreviewUrl=null, spotifyTrackUrl=null, flux={null}, effect=null}, authorWinners=[], titleWinners=[], losers=[]", musicResult.toString() );
    }

}