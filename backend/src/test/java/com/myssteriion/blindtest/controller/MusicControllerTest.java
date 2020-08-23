package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.model.Empty;
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
    public void refresh() {
        
        Mockito.doNothing().when(musicService).init();
        
        ResponseEntity<Empty> re = musicController.refresh();
        Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
    }
    
    @Test
    public void computeThemesInfo() {
        
        List<ThemeInfo> themesInfo = Arrays.asList(
                new ThemeInfo(Theme.ANNEES_60, 2),
                new ThemeInfo(Theme.ANNEES_70, 12),
                new ThemeInfo(Theme.ANNEES_80, 22)
        );
        
        Mockito.when(musicService.computeThemesInfo()).thenReturn(themesInfo);
        
        ResponseEntity< Page<ThemeInfo> > re = musicController.computeThemesInfo();
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( themesInfo, re.getBody().getContent() );
    }
    
    @Test
    public void random() throws NotFoundException, IOException {
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_60).setFlux(fluxMock);
        Mockito.when(musicService.random(null, null)).thenReturn(music);
        Mockito.when(musicService.random(Collections.singletonList(Theme.ANNEES_60), Collections.singletonList(Effect.NONE))).thenReturn(music);
        
        ResponseEntity<MusicEntity> re = musicController.random(null, null);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals(music, re.getBody() );
        
        re = musicController.random(null, null);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals(music, re.getBody() );
        
        re = musicController.random(Collections.singletonList(Theme.ANNEES_60), Collections.singletonList(Effect.NONE));
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals(music, re.getBody() );
    }
    
}
