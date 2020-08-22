package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MusicControllerTest extends AbstractTest {
    
    @Mock
    private MusicService musicService;
    
    @InjectMocks
    private MusicController musicController;
    
    
    
    @Test
    public void computeThemesInfo() {
        
        Mockito.doNothing().when(musicService).refresh();
        
        List<ThemeInfo> themesInfo = Arrays.asList(
                new ThemeInfo(Theme.ANNEES_60, 2, 4),
                new ThemeInfo(Theme.ANNEES_70, 12, 14),
                new ThemeInfo(Theme.ANNEES_80, 22, 24)
        );
        
        Mockito.when(musicService.computeThemesInfo()).thenReturn(themesInfo);
        
        ResponseEntity< Page<ThemeInfo> > re = musicController.computeThemesInfo();
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( themesInfo, re.getBody().getContent() );
    }
    
    @Test
    public void random() throws NotFoundException, IOException, SpotifyException {
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_60, ConnectionMode.OFFLINE).setFlux(fluxMock);
        Mockito.when(musicService.random(null, null, ConnectionMode.OFFLINE)).thenReturn(music);
        Mockito.when(musicService.random(Collections.singletonList(Theme.ANNEES_60), Collections.singletonList(Effect.NONE), ConnectionMode.OFFLINE)).thenReturn(music);
        
        ResponseEntity<MusicEntity> re = musicController.random(null, null, ConnectionMode.OFFLINE);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals(music, re.getBody() );
        
        re = musicController.random(null, null, ConnectionMode.OFFLINE);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals(music, re.getBody() );
        
        re = musicController.random(Collections.singletonList(Theme.ANNEES_60), Collections.singletonList(Effect.NONE), ConnectionMode.OFFLINE);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals(music, re.getBody() );
    }
    
}
