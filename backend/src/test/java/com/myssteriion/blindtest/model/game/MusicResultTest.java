package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MusicResultTest extends AbstractTest {
    
    @Test
    public void getterSetter() {
        
        Integer gameId = 1;
        MusicDTO music = new MusicDTO("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        
        
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music);
        Assert.assertEquals( gameId, musicResult.getGameId() );
        Assert.assertEquals( music, musicResult.getMusic() );
        Assert.assertNull( musicResult.getAuthorWinners() );
        Assert.assertNull( musicResult.getTitleWinners() );
        Assert.assertNull( musicResult.getLosers() );
        Assert.assertNull( musicResult.getPenalties() );
    }
    
    @Test
    public void toStringAndEquals() {
        
        Integer gameId = 1;
        MusicDTO music = new MusicDTO("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        MusicResult musicResult = new MusicResult().setGameId(gameId).setMusic(music);
        Assert.assertEquals( "gameId=1, music={id=null, name=name, theme=ANNEES_80, played=0, connectionMode=OFFLINE, spotifyTrackId=null, spotifyPreviewUrl=null, spotifyTrackUrl=null, flux={null}, effect=null}, authorWinners=null, titleWinners=null, losers=null, penalties=null", musicResult.toString() );
    }
    
}