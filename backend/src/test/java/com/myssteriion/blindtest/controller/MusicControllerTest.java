package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.MusicFilter;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.model.entity.impl.Flux;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class MusicControllerTest extends AbstractTest {
    
    @Mock
    private MusicService musicService;
    
    @InjectMocks
    private MusicController musicController;
    
    
    
    @Test
    void refresh() {
        
        Mockito.doNothing().when(musicService).init();
        
        ResponseEntity<Empty> re = musicController.refresh();
        Assertions.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
    }
    
    @Test
    void computeThemesInfo() {
        
        List<ThemeInfo> themesInfo = Arrays.asList(
                new ThemeInfo(Theme.ANNEES_60, 2),
                new ThemeInfo(Theme.ANNEES_70, 12),
                new ThemeInfo().setTheme(Theme.ANNEES_80).setNbMusics(22)
        );
        
        
        Mockito.when(musicService.computeThemesInfo()).thenReturn(themesInfo);
        
        ResponseEntity< Page<ThemeInfo> > re = musicController.computeThemesInfo();
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertEquals( themesInfo, re.getBody().getContent() );
    }
    
    @Test
    void random() throws NotFoundException, IOException {
        
        MusicFilter musicFilter = new MusicFilter().setThemes( Theme.getSortedTheme() ).setEffects( Effect.getSortedEffect() );
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_60).setFlux(fluxMock);
        Mockito.when(musicService.random(null)).thenReturn(music);
        Mockito.when(musicService.random(musicFilter)).thenReturn(music);
        
        ResponseEntity<MusicEntity> re = musicController.random(null);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertEquals(music, re.getBody() );
        
        re = musicController.random(null);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertEquals(music, re.getBody() );
        
        re = musicController.random(musicFilter);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertEquals(music, re.getBody() );
    }
    
}
